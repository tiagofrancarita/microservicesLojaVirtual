SELECT constraint_name from information_schema.constraint_column_usage
WHERE table_name = 'usuario_acesso' and column_name = 'acessoid'
and constraint_name <> 'unique_acesso_user';

ALTER TABLE usuario_acesso DROP CONSTRAINT "uk_kfpl2gajok99agpt6eejiafsx";