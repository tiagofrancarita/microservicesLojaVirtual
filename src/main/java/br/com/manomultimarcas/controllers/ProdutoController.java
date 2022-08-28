package br.com.manomultimarcas.controllers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import br.com.manomultimarcas.model.Produto;
import br.com.manomultimarcas.repository.ProdutoRepository;
import br.com.manomultimarcas.services.ServiceSendEmail;
import br.com.manomultimarcas.util.ExceptionLojaVirtual;

@Controller
public class ProdutoController {

	private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ServiceSendEmail serviceSendEmail;

	@ResponseBody // Poder dar um retorno da API
	@PostMapping(value = "**/salvarProduto") // URL para receber o json
	public ResponseEntity<Produto> salvarProduto(@RequestBody @Valid Produto produto)
			throws ExceptionLojaVirtual, MessagingException, IOException {

		logger.info("Cadastro de produto iniciado.");

		if (produto.getTipoUnidade() == null || produto.getTipoUnidade().isEmpty()) {
			logger.error("Cadastro de produto encerrado com erro.");
			logger.error("Tipo da unidade deve ser informada.");
			throw new ExceptionLojaVirtual("Tipo da unidade deve ser informada.");

		}

		if (produto.getNome().length() < 10) {
			logger.error("Cadastro de produto encerrado com erro.");
			logger.error("Nome do produto deve ter mais que 10 caracteres.");
			throw new ExceptionLojaVirtual("Nome do produto deve ter mais que 10 caracteres.");
		}

		if (produto.getEmpresa() == null || produto.getEmpresa().getId() <= 0) {
			logger.error("Cadastro de produto encerrado com erro.");
			logger.error("Empresa responsável deve ser informada.");
			throw new ExceptionLojaVirtual("Empresa responsável deve ser informada.");
		}

		if (produto.getId() == null) {
			List<Produto> produtos = produtoRepository.buscarProdutoNome(produto.getDescricao().toUpperCase(),
					produto.getEmpresa().getId());
			if (!produtos.isEmpty()) {
				logger.error("Cadastro de produto encerrado com erro.");
				logger.error("Já existe um produto com essa descrição." + produto.getNome());
				throw new ExceptionLojaVirtual("Já existe produto");
			}

			if (produto.getCategoriaProduto() == null || produto.getCategoriaProduto().getId() <= 0) {
				logger.error("Cadastro de produto encerrado com erro.");
				logger.error("Categoria do produto deve ser imformada.");
				throw new ExceptionLojaVirtual("Categoria do produto deve ser informada.");
			}
			if (produto.getMarcaProduto() == null || produto.getMarcaProduto().getId() <= 0) {
				logger.error("Cadastro de produto encerrado com erro.");
				logger.error("Marca do produto deve ser imformada.");
				throw new ExceptionLojaVirtual("Marca do produto deve ser informada.");
			}

			if (produto.getQuantidadeEstoque() < 1) {
				logger.error("Cadastro de produto encerrado com erro.");
				logger.error("O produto cadastrado deve ter no minímo 1 no estoque.");
				throw new ExceptionLojaVirtual("O produto cadastrado deve ter no minímo 1 no estoque.");
			}

			if (produto.getImagens() == null || produto.getImagens().isEmpty() || produto.getImagens().size() == 0) {
				logger.error("Cadastro de produto encerrado com erro.");
				logger.error("A imagem do produto deve ser cadastrada.");
				throw new ExceptionLojaVirtual("A imagem do produto deve ser cadastrada.");
			}

			if (produto.getImagens().size() < 3) {
				logger.error("Cadastro de produto encerrado com erro.");
				logger.error("O produto cadastrado deve ter no minímo 3 imagens.");
				throw new ExceptionLojaVirtual("O produto cadastrado deve ter no minímo 3 imagens.");
			}
			if (produto.getImagens().size() > 6) {
				logger.error("Cadastro de produto encerrado com erro.");
				logger.error("O produto cadastrado deve ter no máximo 6 imagens.");
				throw new ExceptionLojaVirtual("O produto cadastrado deve ter no máximo 6 imagens.");
			}

			if (produto.getId() == null) {

				for (int x = 0; x < produto.getImagens().size(); x++) {
					produto.getImagens().get(x).setProduto(produto);
					produto.getImagens().get(x).setEmpresa(produto.getEmpresa());

					String base64Image = "";

					if (produto.getImagens().get(x).getImagemOriginal().contains("data:image")) {
						base64Image = produto.getImagens().get(x).getImagemOriginal().split(",")[1];

					} else {
						base64Image = produto.getImagens().get(x).getImagemOriginal();
					}

					byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);

					BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

					if (bufferedImage != null) {
						
						int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
						int largura = Integer.parseInt("800");
						int altura = Integer.parseInt("600");
						
						BufferedImage resizedImage = new BufferedImage(largura, altura, type);
						Graphics2D g = resizedImage.createGraphics();
						g.drawImage(bufferedImage, 0, 0, largura, altura, null);
						g.dispose();
						
						ByteArrayOutputStream saidaImagem = new ByteArrayOutputStream();
						ImageIO.write(resizedImage, "png", saidaImagem);
						
						String miniImgBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(saidaImagem.toByteArray());
						
						produto.getImagens().get(x).setImagemMiniatura(miniImgBase64);
						
						bufferedImage.flush();
						resizedImage.flush();
						saidaImagem.flush();
						saidaImagem.close();
												
					}
				}
			}
		}
		
		logger.info("Produto cadastrado.");
		logger.info("Cadastro de produto finalizado com sucesso.");
		Produto produtoSalvo = produtoRepository.save(produto);

		if (produto.getAlertaQuantidadeEstoque() && produto.getQuantidadeEstoque() <= 1) {

			StringBuilder emailEstoquebaixo = new StringBuilder();
			emailEstoquebaixo.append("<h2>").append("Atenção, o produto:" + produto.getNome())
					.append("está com a quantidade em estoque abaixo do minímo, quantidade em estoque atual:"
							+ produto.getQuantidadeEstoque());
			emailEstoquebaixo.append("<p> Código do produto:").append(produto.getId()).append("</p>");

			if (produto.getEmpresa().getEmail() != null) {
				serviceSendEmail.enviaEmailHtml("Produto com estoque baixo", emailEstoquebaixo.toString(),
						produto.getEmpresa().getEmail());
			}

		}

		return new ResponseEntity<Produto>(produtoSalvo, HttpStatus.OK);

	}

	@ResponseBody // Poder dar um retorno da API
	@PostMapping(value = "**/deletarProduto") // URL para receber o json
	public ResponseEntity<String> deletarProduto(@RequestBody Produto produto) {// Recebe o json e converte para objeto

		produtoRepository.deleteById(produto.getId());
		return new ResponseEntity<String>("Produto excluído", HttpStatus.OK);

	}

	@ResponseBody
	@DeleteMapping(value = "**/deletarProdutoPorId/{id}") // URL para receber o json
	public ResponseEntity<String> deletarProdutoPorId(@PathVariable("id") Long id) {// Recebe o json e converte para
																					// objeto

		produtoRepository.deleteById(id);

		return new ResponseEntity<String>("Produto excluído", HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping(value = "**/obterProdutoId/{id}") // URL para receber o json
	public ResponseEntity<?> obterProdutoId(@PathVariable("id") Long id) throws ExceptionLojaVirtual {// Recebe o json e

		Produto produto = produtoRepository.findById(id).orElse(null);

		if (produto == null) {
			throw new ExceptionLojaVirtual("Código informado não existe." + "Código: " + id);
		}

		return new ResponseEntity<Produto>(produto, HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping(value = "**/obterProdutoNome/{desc}") // URL para receber o json
	public ResponseEntity<List<Produto>> obterProdutoNome(@PathVariable("desc") String desc) {// Recebe o json e
																								// converte para objeto

		List<Produto> produto = produtoRepository.buscarProdutoNome(desc.toUpperCase());
		return new ResponseEntity<List<Produto>>(produto, HttpStatus.OK);

	}
}