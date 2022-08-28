SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;

--DROP DATABASE "manomultimarcas_prod";
--
-- TOC entry 2250 (class 1262 OID 24576)
-- Name: manomultimarcas_prod; Type: DATABASE; Schema: -; Owner: -
--

--CREATE DATABASE "manomultimarcas_prod" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Portuguese_Brazil.1252' LC_CTYPE = 'Portuguese_Brazil.1252';
-- manomultimarcas_prod
-- manomultimarcas_homol
-- manomultimarcas_develop

ALTER DATABASE manomultimarcas_develop OWNER TO postgres;

--\connect "manomultimarcas"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;

--
-- TOC entry 2251 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA "public"; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA "public" IS 'standard public schema';


--
-- TOC entry 1 (class 3079 OID 11855)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "plpgsql" WITH SCHEMA "pg_catalog";


--
-- TOC entry 2252 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION "plpgsql"; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION "plpgsql" IS 'PL/pgSQL procedural language';


--
-- TOC entry 226 (class 1255 OID 114924)
-- Name: validarchavepessoa(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION "public"."validarchavepessoa"() RETURNS "trigger"
    LANGUAGE "plpgsql"
    AS '
 		/* NEW carrega os dados de insert e update */
		/* OLD carrega os dados da linha antiga antes de atualizar */
DECLARE existe integer;

	BEGIN
	
		existe = (SELECT  COUNT(1) FROM pessoa_fisica WHERE id = NEW.pessoaid);
		IF (existe <= 0) THEN
			existe = (SELECT  COUNT(1) FROM pessoa_juridica WHERE id = NEW.pessoaid);
		IF (existe <= 0) THEN
			RAISE EXCEPTION ''Não foi encontrado ID ou PK da pessoa para realizar a associação'';
			
		END IF;
		END IF;
	RETURN NEW;
	END;
	';


--
-- TOC entry 227 (class 1255 OID 114935)
-- Name: validarchavepessoaforn(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION "public"."validarchavepessoaforn"() RETURNS "trigger"
    LANGUAGE "plpgsql"
    AS '
 		/* NEW carrega os dados de insert e update */
		/* OLD carrega os dados da linha antiga antes de atualizar */
DECLARE existe integer;

	BEGIN
	
		existe = (SELECT  COUNT(1) FROM pessoa_fisica WHERE id = NEW.pessoa_fornecedorid);
		IF (existe <= 0) THEN
			existe = (SELECT  COUNT(1) FROM pessoa_juridica WHERE id = NEW.pessoa_fornecedorid);
		IF (existe <= 0) THEN
			RAISE EXCEPTION ''Não foi encontrado ID ou PK da pessoa para realizar a associação'';
			
		END IF;
		END IF;
	RETURN NEW;
	END;
	';


SET default_with_oids = false;

--
-- TOC entry 191 (class 1259 OID 90347)
-- Name: acesso; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."acesso" (
    "id" bigint NOT NULL,
    "descricao" character varying(255) NOT NULL
);


--
-- TOC entry 198 (class 1259 OID 106741)
-- Name: avaliacao_produto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."avaliacao_produto" (
    "id" bigint NOT NULL,
    "descricao" character varying(255) NOT NULL,
    "nota" integer NOT NULL,
    "pessoaid" bigint NOT NULL,
    "produtoid" bigint NOT NULL
);


--
-- TOC entry 192 (class 1259 OID 90357)
-- Name: categoria_produto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."categoria_produto" (
    "id" bigint NOT NULL,
    "descricao_categoria" character varying(255) NOT NULL
);


--
-- TOC entry 199 (class 1259 OID 106764)
-- Name: conta_pagar; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."conta_pagar" (
    "id" bigint NOT NULL,
    "descricao" character varying(255) NOT NULL,
    "dt_pagamento" "date",
    "dt_vencimento" "date" NOT NULL,
    "status_conta_pagar" character varying(255) NOT NULL,
    "valor_desconto" numeric(19,2),
    "valor_total" numeric(19,2) NOT NULL,
    "pessoaid" bigint NOT NULL,
    "pessoa_fornecedorid" bigint NOT NULL
);


--
-- TOC entry 200 (class 1259 OID 106772)
-- Name: conta_receber; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."conta_receber" (
    "id" bigint NOT NULL,
    "descricao" character varying(255) NOT NULL,
    "dt_pagamento" "date",
    "dt_vencimento" "date" NOT NULL,
    "status_conta_receber" character varying(255) NOT NULL,
    "valor_desconto" numeric(19,2),
    "valor_total" numeric(19,2) NOT NULL,
    "pessoaid" bigint NOT NULL,
    "pessoa_fornecedorid" bigint NOT NULL
);


--
-- TOC entry 201 (class 1259 OID 106780)
-- Name: cupom_desconto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."cupom_desconto" (
    "id" bigint NOT NULL,
    "codigo_descricao" character varying(255) NOT NULL,
    "data_validade_cupom" "date" NOT NULL,
    "valor_porcent_desconto" numeric(19,2),
    "valor_real_desconto" numeric(19,2)
);


--
-- TOC entry 202 (class 1259 OID 106785)
-- Name: endereco; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."endereco" (
    "id" bigint NOT NULL,
    "bairro" character varying(255) NOT NULL,
    "cep" character varying(255) NOT NULL,
    "cidade" character varying(255) NOT NULL,
    "complemento" character varying(255) NOT NULL,
    "logradouro" character varying(255) NOT NULL,
    "numero" character varying(255) NOT NULL,
    "tipo_endereco" character varying(255) NOT NULL,
    "uf" character varying(255) NOT NULL,
    "pessoaid" bigint NOT NULL
);


--
-- TOC entry 203 (class 1259 OID 106793)
-- Name: forma_pagamento; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."forma_pagamento" (
    "id" bigint NOT NULL,
    "descricao" character varying(255) NOT NULL
);


--
-- TOC entry 204 (class 1259 OID 106823)
-- Name: imagem_produto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."imagem_produto" (
    "id" bigint NOT NULL,
    "imagem_miniatura" "text" NOT NULL,
    "imagem_original" character varying(255) NOT NULL,
    "produtoid" bigint NOT NULL
);


--
-- TOC entry 205 (class 1259 OID 106831)
-- Name: item_venda_loja; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."item_venda_loja" (
    "id" bigint NOT NULL,
    "quantidade" double precision NOT NULL,
    "produtoid" bigint NOT NULL,
    "venda_compra_loja_virtualid" bigint NOT NULL
);


--
-- TOC entry 193 (class 1259 OID 90409)
-- Name: marca_produto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."marca_produto" (
    "id" bigint NOT NULL,
    "descricao_marca" character varying(255) NOT NULL
);


--
-- TOC entry 206 (class 1259 OID 106852)
-- Name: nota_fiscal_compra; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."nota_fiscal_compra" (
    "id" bigint NOT NULL,
    "data_compra" "date" NOT NULL,
    "descricao_obs" character varying(255),
    "numero_nota" character varying(255) NOT NULL,
    "serie_nota" character varying(255) NOT NULL,
    "valor_desconto" numeric(19,2),
    "valoricms" numeric(19,2) NOT NULL,
    "valor_total" numeric(19,2) NOT NULL,
    "conta_pagarid" bigint NOT NULL,
    "pessoaid" bigint NOT NULL
);


--
-- TOC entry 207 (class 1259 OID 106870)
-- Name: nota_fiscal_venda; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."nota_fiscal_venda" (
    "id" bigint NOT NULL,
    "notapdf" "text" NOT NULL,
    "notaxml" "text" NOT NULL,
    "numero_nota" character varying(255) NOT NULL,
    "serie_nota" character varying(255) NOT NULL,
    "tipo_nota" character varying(255) NOT NULL,
    "venda_compra_loja_virtualid" bigint NOT NULL
);


--
-- TOC entry 194 (class 1259 OID 90430)
-- Name: nota_item_produto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."nota_item_produto" (
    "id" bigint NOT NULL,
    "quantidade" double precision NOT NULL,
    "nota_fiscal_compraid" bigint NOT NULL,
    "produtoid" bigint NOT NULL
);


--
-- TOC entry 208 (class 1259 OID 106888)
-- Name: pessoa_fisica; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."pessoa_fisica" (
    "id" bigint NOT NULL,
    "email" character varying(255) NOT NULL,
    "nome" character varying(255) NOT NULL,
    "telefone" character varying(255) NOT NULL,
    "cpf" character varying(255) NOT NULL,
    "data_nascimento" "date" NOT NULL
);


--
-- TOC entry 209 (class 1259 OID 106896)
-- Name: pessoa_juridica; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."pessoa_juridica" (
    "id" bigint NOT NULL,
    "email" character varying(255) NOT NULL,
    "nome" character varying(255) NOT NULL,
    "telefone" character varying(255) NOT NULL,
    "categoria" character varying(255) NOT NULL,
    "cnpj" character varying(255) NOT NULL,
    "insc_estadual" character varying(255) NOT NULL,
    "insc_munincipal" character varying(255) NOT NULL,
    "nome_fantasia" character varying(255) NOT NULL,
    "razao_social" character varying(255) NOT NULL
);


--
-- TOC entry 212 (class 1259 OID 123121)
-- Name: produto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."produto" (
    "id" bigint NOT NULL,
    "alerta_quantidade_estoque" boolean,
    "altura" double precision NOT NULL,
    "ativo" boolean NOT NULL,
    "descricao" "text" NOT NULL,
    "largura" double precision NOT NULL,
    "link_youtube" character varying(255),
    "nome" character varying(255) NOT NULL,
    "peso" double precision NOT NULL,
    "profundidade" double precision NOT NULL,
    "quantidade_alerta_estoque" integer,
    "quantidade_clique" integer,
    "quantidade_estoque" integer NOT NULL,
    "tipo_unidade" character varying(255) NOT NULL,
    "valor_venda" numeric(19,2) NOT NULL,
    "nota_item_produtoid" bigint NOT NULL
);


--
-- TOC entry 173 (class 1259 OID 57556)
-- Name: seq_acesso; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_acesso"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 189 (class 1259 OID 82232)
-- Name: seq_avaliacao_produto; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_avaliacao_produto"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 174 (class 1259 OID 57558)
-- Name: seq_categoria_produto; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_categoria_produto"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 181 (class 1259 OID 57606)
-- Name: seq_conta_pagar; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_conta_pagar"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 179 (class 1259 OID 57589)
-- Name: seq_conta_receber; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_conta_receber"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 182 (class 1259 OID 73968)
-- Name: seq_cupom_desconto; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_cupom_desconto"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 175 (class 1259 OID 57560)
-- Name: seq_endereco_cobranca; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_endereco_cobranca"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 180 (class 1259 OID 57596)
-- Name: seq_forma_pagamento; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_forma_pagamento"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 184 (class 1259 OID 82176)
-- Name: seq_imagem_produto; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_imagem_produto"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 176 (class 1259 OID 57562)
-- Name: seq_marca_produto; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_marca_produto"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 185 (class 1259 OID 82178)
-- Name: seq_nota_fiscal_compra; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_nota_fiscal_compra"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 197 (class 1259 OID 90487)
-- Name: seq_nota_item_produto; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_nota_item_produto"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 177 (class 1259 OID 57564)
-- Name: seq_pessoa; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_pessoa"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 183 (class 1259 OID 73978)
-- Name: seq_produto; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_produto"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 187 (class 1259 OID 82195)
-- Name: seq_status_nota_fiscal_venda; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_status_nota_fiscal_venda"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 186 (class 1259 OID 82180)
-- Name: seq_status_rastreio; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_status_rastreio"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 178 (class 1259 OID 57566)
-- Name: seq_usuario; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seq_usuario"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 190 (class 1259 OID 82234)
-- Name: seqitem_venda_loja; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seqitem_venda_loja"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 188 (class 1259 OID 82202)
-- Name: seqvd_cp_loja_virtual; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "public"."seqvd_cp_loja_virtual"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 195 (class 1259 OID 90459)
-- Name: status_rastreio; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."status_rastreio" (
    "id" bigint NOT NULL,
    "centro_distribuicao" character varying(255),
    "cidade" character varying(255),
    "estado" character varying(255),
    "status" character varying(255),
    "venda_compra_loja_virtualid" bigint NOT NULL
);

--
-- TOC entry 210 (class 1259 OID 106951)
-- Name: usuario; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."usuario" (
    "id" bigint NOT NULL,
    "data_atual_senha" "date" NOT NULL,
    "login" character varying(255) NOT NULL,
    "senha" character varying(255) NOT NULL,
    "pessoaid" bigint NOT NULL
);


--
-- TOC entry 196 (class 1259 OID 90475)
-- Name: usuario_acesso; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."usuario_acesso" (
    "usuarioid" bigint NOT NULL,
    "acessoid" bigint NOT NULL
);


--
-- TOC entry 211 (class 1259 OID 106959)
-- Name: vd_cp_loja_virtual; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "public"."vd_cp_loja_virtual" (
    "id" bigint NOT NULL,
    "dias_entrega" integer NOT NULL,
    "dt_entrega" "date" NOT NULL,
    "dt_venda" "date" NOT NULL,
    "valor_desconto" numeric(19,2),
    "valor_frete" numeric(19,2) NOT NULL,
    "valor_total" numeric(19,2) NOT NULL,
    "cupom_descontoid" bigint NOT NULL,
    "endereco_cobrancaid" bigint NOT NULL,
    "endereco_entregaid" bigint NOT NULL,
    "forma_pagamentoid" bigint NOT NULL,
    "nota_fiscal_vendaid" bigint NOT NULL,
    "pessoaid" bigint NOT NULL
);


--
-- TOC entry 2222 (class 0 OID 90347)
-- Dependencies: 191
-- Data for Name: acesso; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2229 (class 0 OID 106741)
-- Dependencies: 198
-- Data for Name: avaliacao_produto; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2223 (class 0 OID 90357)
-- Dependencies: 192
-- Data for Name: categoria_produto; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2230 (class 0 OID 106764)
-- Dependencies: 199
-- Data for Name: conta_pagar; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2231 (class 0 OID 106772)
-- Dependencies: 200
-- Data for Name: conta_receber; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2232 (class 0 OID 106780)
-- Dependencies: 201
-- Data for Name: cupom_desconto; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2233 (class 0 OID 106785)
-- Dependencies: 202
-- Data for Name: endereco; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2234 (class 0 OID 106793)
-- Dependencies: 203
-- Data for Name: forma_pagamento; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2235 (class 0 OID 106823)
-- Dependencies: 204
-- Data for Name: imagem_produto; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2236 (class 0 OID 106831)
-- Dependencies: 205
-- Data for Name: item_venda_loja; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2224 (class 0 OID 90409)
-- Dependencies: 193
-- Data for Name: marca_produto; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2237 (class 0 OID 106852)
-- Dependencies: 206
-- Data for Name: nota_fiscal_compra; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2238 (class 0 OID 106870)
-- Dependencies: 207
-- Data for Name: nota_fiscal_venda; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2225 (class 0 OID 90430)
-- Dependencies: 194
-- Data for Name: nota_item_produto; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2239 (class 0 OID 106888)
-- Dependencies: 208
-- Data for Name: pessoa_fisica; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2240 (class 0 OID 106896)
-- Dependencies: 209
-- Data for Name: pessoa_juridica; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2243 (class 0 OID 123121)
-- Dependencies: 212
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2253 (class 0 OID 0)
-- Dependencies: 173
-- Name: seq_acesso; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_acesso"', 1, false);


--
-- TOC entry 2254 (class 0 OID 0)
-- Dependencies: 189
-- Name: seq_avaliacao_produto; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_avaliacao_produto"', 1, false);


--
-- TOC entry 2255 (class 0 OID 0)
-- Dependencies: 174
-- Name: seq_categoria_produto; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_categoria_produto"', 1, false);


--
-- TOC entry 2256 (class 0 OID 0)
-- Dependencies: 181
-- Name: seq_conta_pagar; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_conta_pagar"', 1, false);


--
-- TOC entry 2257 (class 0 OID 0)
-- Dependencies: 179
-- Name: seq_conta_receber; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_conta_receber"', 1, false);


--
-- TOC entry 2258 (class 0 OID 0)
-- Dependencies: 182
-- Name: seq_cupom_desconto; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_cupom_desconto"', 1, false);


--
-- TOC entry 2259 (class 0 OID 0)
-- Dependencies: 175
-- Name: seq_endereco_cobranca; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_endereco_cobranca"', 1, false);


--
-- TOC entry 2260 (class 0 OID 0)
-- Dependencies: 180
-- Name: seq_forma_pagamento; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_forma_pagamento"', 1, false);


--
-- TOC entry 2261 (class 0 OID 0)
-- Dependencies: 184
-- Name: seq_imagem_produto; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_imagem_produto"', 1, false);


--
-- TOC entry 2262 (class 0 OID 0)
-- Dependencies: 176
-- Name: seq_marca_produto; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_marca_produto"', 1, false);


--
-- TOC entry 2263 (class 0 OID 0)
-- Dependencies: 185
-- Name: seq_nota_fiscal_compra; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_nota_fiscal_compra"', 1, false);


--
-- TOC entry 2264 (class 0 OID 0)
-- Dependencies: 197
-- Name: seq_nota_item_produto; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_nota_item_produto"', 1, false);


--
-- TOC entry 2265 (class 0 OID 0)
-- Dependencies: 177
-- Name: seq_pessoa; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_pessoa"', 1, false);


--
-- TOC entry 2266 (class 0 OID 0)
-- Dependencies: 183
-- Name: seq_produto; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_produto"', 1, false);


--
-- TOC entry 2267 (class 0 OID 0)
-- Dependencies: 187
-- Name: seq_status_nota_fiscal_venda; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_status_nota_fiscal_venda"', 1, false);


--
-- TOC entry 2268 (class 0 OID 0)
-- Dependencies: 186
-- Name: seq_status_rastreio; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_status_rastreio"', 1, false);


--
-- TOC entry 2269 (class 0 OID 0)
-- Dependencies: 178
-- Name: seq_usuario; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seq_usuario"', 1, false);


--
-- TOC entry 2270 (class 0 OID 0)
-- Dependencies: 190
-- Name: seqitem_venda_loja; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seqitem_venda_loja"', 1, false);


--
-- TOC entry 2271 (class 0 OID 0)
-- Dependencies: 188
-- Name: seqvd_cp_loja_virtual; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."seqvd_cp_loja_virtual"', 1, false);


--
-- TOC entry 2226 (class 0 OID 90459)
-- Dependencies: 195
-- Data for Name: status_rastreio; Type: TABLE DATA; Schema: public; Owner: -
--

--
-- TOC entry 2241 (class 0 OID 106951)
-- Dependencies: 210
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2227 (class 0 OID 90475)
-- Dependencies: 196
-- Data for Name: usuario_acesso; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2242 (class 0 OID 106959)
-- Dependencies: 211
-- Data for Name: vd_cp_loja_virtual; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2015 (class 2606 OID 90351)
-- Name: acesso_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."acesso"
    ADD CONSTRAINT "acesso_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2029 (class 2606 OID 106745)
-- Name: avaliacao_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."avaliacao_produto"
    ADD CONSTRAINT "avaliacao_produto_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2017 (class 2606 OID 90361)
-- Name: categoria_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."categoria_produto"
    ADD CONSTRAINT "categoria_produto_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2031 (class 2606 OID 106771)
-- Name: conta_pagar_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."conta_pagar"
    ADD CONSTRAINT "conta_pagar_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2033 (class 2606 OID 106779)
-- Name: conta_receber_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."conta_receber"
    ADD CONSTRAINT "conta_receber_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2035 (class 2606 OID 106784)
-- Name: cupom_desconto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."cupom_desconto"
    ADD CONSTRAINT "cupom_desconto_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2037 (class 2606 OID 106792)
-- Name: endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."endereco"
    ADD CONSTRAINT "endereco_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2039 (class 2606 OID 106797)
-- Name: forma_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."forma_pagamento"
    ADD CONSTRAINT "forma_pagamento_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2041 (class 2606 OID 106830)
-- Name: imagem_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."imagem_produto"
    ADD CONSTRAINT "imagem_produto_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2043 (class 2606 OID 106835)
-- Name: item_venda_loja_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."item_venda_loja"
    ADD CONSTRAINT "item_venda_loja_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2019 (class 2606 OID 90413)
-- Name: marca_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."marca_produto"
    ADD CONSTRAINT "marca_produto_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2045 (class 2606 OID 106859)
-- Name: nota_fiscal_compra_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."nota_fiscal_compra"
    ADD CONSTRAINT "nota_fiscal_compra_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2047 (class 2606 OID 106877)
-- Name: nota_fiscal_venda_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."nota_fiscal_venda"
    ADD CONSTRAINT "nota_fiscal_venda_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2021 (class 2606 OID 90434)
-- Name: nota_item_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."nota_item_produto"
    ADD CONSTRAINT "nota_item_produto_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2049 (class 2606 OID 106895)
-- Name: pessoa_fisica_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."pessoa_fisica"
    ADD CONSTRAINT "pessoa_fisica_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2051 (class 2606 OID 106903)
-- Name: pessoa_juridica_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."pessoa_juridica"
    ADD CONSTRAINT "pessoa_juridica_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2057 (class 2606 OID 123128)
-- Name: produto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."produto"
    ADD CONSTRAINT "produto_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2023 (class 2606 OID 90466)
-- Name: status_rastreio_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."status_rastreio"
    ADD CONSTRAINT "status_rastreio_pkey" PRIMARY KEY ("id");

--
-- TOC entry 2025 (class 2606 OID 90576)
-- Name: uk_kfpl2gajok99agpt6eejiafsx; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."usuario_acesso"
    ADD CONSTRAINT "uk_kfpl2gajok99agpt6eejiafsx" UNIQUE ("acessoid");


--
-- TOC entry 2027 (class 2606 OID 90486)
-- Name: uniqueacessouser; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."usuario_acesso"
    ADD CONSTRAINT "uniqueacessouser" UNIQUE ("usuarioid", "acessoid");


--
-- TOC entry 2053 (class 2606 OID 106958)
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."usuario"
    ADD CONSTRAINT "usuario_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2055 (class 2606 OID 106963)
-- Name: vd_cp_loja_virtual_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."vd_cp_loja_virtual"
    ADD CONSTRAINT "vd_cp_loja_virtual_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2078 (class 2620 OID 114939)
-- Name: validarchavepessoaavaliacaoprodutoinsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoaavaliacaoprodutoinsert" BEFORE INSERT ON "public"."avaliacao_produto" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoa"();


--
-- TOC entry 2077 (class 2620 OID 114938)
-- Name: validarchavepessoaavaliacaoprodutoupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoaavaliacaoprodutoupdate" BEFORE UPDATE ON "public"."avaliacao_produto" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoa"();


--
-- TOC entry 2080 (class 2620 OID 114941)
-- Name: validarchavepessoacontapagarinsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoacontapagarinsert" BEFORE INSERT ON "public"."conta_pagar" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoa"();


--
-- TOC entry 2079 (class 2620 OID 114940)
-- Name: validarchavepessoacontapagarupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoacontapagarupdate" BEFORE UPDATE ON "public"."conta_pagar" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoa"();


--
-- TOC entry 2084 (class 2620 OID 114945)
-- Name: validarchavepessoacontareceberinsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoacontareceberinsert" BEFORE INSERT ON "public"."conta_receber" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoa"();


--
-- TOC entry 2083 (class 2620 OID 114944)
-- Name: validarchavepessoacontareceberupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoacontareceberupdate" BEFORE UPDATE ON "public"."conta_receber" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoa"();


--
-- TOC entry 2088 (class 2620 OID 114949)
-- Name: validarchavepessoaenderecoinsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoaenderecoinsert" BEFORE INSERT ON "public"."endereco" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoa"();


--
-- TOC entry 2087 (class 2620 OID 114948)
-- Name: validarchavepessoaenderecoupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoaenderecoupdate" BEFORE UPDATE ON "public"."endereco" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoa"();


--
-- TOC entry 2082 (class 2620 OID 114943)
-- Name: validarchavepessoaforncontapagarinsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoaforncontapagarinsert" BEFORE INSERT ON "public"."conta_pagar" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoaforn"();


--
-- TOC entry 2081 (class 2620 OID 114942)
-- Name: validarchavepessoaforncontapagarupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoaforncontapagarupdate" BEFORE UPDATE ON "public"."conta_pagar" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoaforn"();


--
-- TOC entry 2086 (class 2620 OID 114947)
-- Name: validarchavepessoaforncontareceberinsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoaforncontareceberinsert" BEFORE INSERT ON "public"."conta_receber" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoaforn"();


--
-- TOC entry 2085 (class 2620 OID 114946)
-- Name: validarchavepessoaforncontareceberupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoaforncontareceberupdate" BEFORE UPDATE ON "public"."conta_receber" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoaforn"();


--
-- TOC entry 2090 (class 2620 OID 114951)
-- Name: validarchavepessoanotafiscacomprainsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoanotafiscacomprainsert" BEFORE INSERT ON "public"."nota_fiscal_compra" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoa"();


--
-- TOC entry 2089 (class 2620 OID 114950)
-- Name: validarchavepessoanotafiscacompraupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoanotafiscacompraupdate" BEFORE UPDATE ON "public"."nota_fiscal_compra" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoa"();


--
-- TOC entry 2092 (class 2620 OID 114953)
-- Name: validarchavepessoausuarioinsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoausuarioinsert" BEFORE INSERT ON "public"."usuario" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoa"();


--
-- TOC entry 2091 (class 2620 OID 114952)
-- Name: validarchavepessoausuarioupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoausuarioupdate" BEFORE UPDATE ON "public"."usuario" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoa"();


--
-- TOC entry 2094 (class 2620 OID 114955)
-- Name: validarchavepessoavendacompralojavirtuinsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoavendacompralojavirtuinsert" BEFORE INSERT ON "public"."vd_cp_loja_virtual" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoa"();


--
-- TOC entry 2093 (class 2620 OID 114954)
-- Name: validarchavepessoavendacompralojavirtuupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER "validarchavepessoavendacompralojavirtuupdate" BEFORE UPDATE ON "public"."vd_cp_loja_virtual" FOR EACH ROW EXECUTE PROCEDURE "public"."validarchavepessoa"();


--
-- TOC entry 2064 (class 2606 OID 90539)
-- Name: acessofk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."usuario_acesso"
    ADD CONSTRAINT "acessofk" FOREIGN KEY ("acessoid") REFERENCES "public"."acesso"("id");


--
-- TOC entry 2069 (class 2606 OID 106860)
-- Name: contapagarfk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."nota_fiscal_compra"
    ADD CONSTRAINT "contapagarfk" FOREIGN KEY ("conta_pagarid") REFERENCES "public"."conta_pagar"("id");


--
-- TOC entry 2071 (class 2606 OID 106984)
-- Name: cupomdescontofk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."vd_cp_loja_virtual"
    ADD CONSTRAINT "cupomdescontofk" FOREIGN KEY ("cupom_descontoid") REFERENCES "public"."cupom_desconto"("id");


--
-- TOC entry 2072 (class 2606 OID 106989)
-- Name: enderecocobrancafk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."vd_cp_loja_virtual"
    ADD CONSTRAINT "enderecocobrancafk" FOREIGN KEY ("endereco_cobrancaid") REFERENCES "public"."endereco"("id");


--
-- TOC entry 2073 (class 2606 OID 106994)
-- Name: enderecoentregafk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."vd_cp_loja_virtual"
    ADD CONSTRAINT "enderecoentregafk" FOREIGN KEY ("endereco_entregaid") REFERENCES "public"."endereco"("id");


--
-- TOC entry 2074 (class 2606 OID 106999)
-- Name: formapagamentofk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."vd_cp_loja_virtual"
    ADD CONSTRAINT "formapagamentofk" FOREIGN KEY ("forma_pagamentoid") REFERENCES "public"."forma_pagamento"("id");


--
-- TOC entry 2061 (class 2606 OID 106865)
-- Name: notafiscalcomprafk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."nota_item_produto"
    ADD CONSTRAINT "notafiscalcomprafk" FOREIGN KEY ("nota_fiscal_compraid") REFERENCES "public"."nota_fiscal_compra"("id");


--
-- TOC entry 2075 (class 2606 OID 107004)
-- Name: notafiscalvendafk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."vd_cp_loja_virtual"
    ADD CONSTRAINT "notafiscalvendafk" FOREIGN KEY ("nota_fiscal_vendaid") REFERENCES "public"."nota_fiscal_venda"("id");


--
-- TOC entry 2076 (class 2606 OID 131332)
-- Name: notaitemprodutofk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."produto"
    ADD CONSTRAINT "notaitemprodutofk" FOREIGN KEY ("nota_item_produtoid") REFERENCES "public"."nota_item_produto"("id");


--
-- TOC entry 2065 (class 2606 OID 131312)
-- Name: produtofk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."avaliacao_produto"
    ADD CONSTRAINT "produtofk" FOREIGN KEY ("produtoid") REFERENCES "public"."produto"("id");


--
-- TOC entry 2066 (class 2606 OID 131317)
-- Name: produtofk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."imagem_produto"
    ADD CONSTRAINT "produtofk" FOREIGN KEY ("produtoid") REFERENCES "public"."produto"("id");


--
-- TOC entry 2068 (class 2606 OID 131322)
-- Name: produtofk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."item_venda_loja"
    ADD CONSTRAINT "produtofk" FOREIGN KEY ("produtoid") REFERENCES "public"."produto"("id");


--
-- TOC entry 2060 (class 2606 OID 131327)
-- Name: produtofk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."nota_item_produto"
    ADD CONSTRAINT "produtofk" FOREIGN KEY ("produtoid") REFERENCES "public"."produto"("id");


--
-- TOC entry 2063 (class 2606 OID 106979)
-- Name: usuariofk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."usuario_acesso"
    ADD CONSTRAINT "usuariofk" FOREIGN KEY ("usuarioid") REFERENCES "public"."usuario"("id");


--
-- TOC entry 2067 (class 2606 OID 106964)
-- Name: vendacompralojavirtualfk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."item_venda_loja"
    ADD CONSTRAINT "vendacompralojavirtualfk" FOREIGN KEY ("venda_compra_loja_virtualid") REFERENCES "public"."vd_cp_loja_virtual"("id");


--
-- TOC entry 2070 (class 2606 OID 106969)
-- Name: vendacompralojavirtualvendacompralojavirtualfk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."nota_fiscal_venda"
    ADD CONSTRAINT "vendacompralojavirtualvendacompralojavirtualfk" FOREIGN KEY ("venda_compra_loja_virtualid") REFERENCES "public"."vd_cp_loja_virtual"("id");


--
-- TOC entry 2062 (class 2606 OID 106974)
-- Name: vendacompralojavirtualvendacompralojavirtualfk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "public"."status_rastreio"
    ADD CONSTRAINT "vendacompralojavirtualvendacompralojavirtualfk" FOREIGN KEY ("venda_compra_loja_virtualid") REFERENCES "public"."vd_cp_loja_virtual"("id");


-- Completed on 2022-01-09 17:16:34

--
-- PostgreSQL database dump complete
--

