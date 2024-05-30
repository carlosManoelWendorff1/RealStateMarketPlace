FROM jboss/keycloak:latest

# Copie arquivos de configuração ou scripts personalizados, se necessário
COPY my-config.json /opt/jboss/keycloak/standalone/configuration/my-config.json

# Comando para importar as configurações durante a inicialização
ENTRYPOINT ["/opt/jboss/keycloak/bin/standalone.sh", "-Djboss.socket.binding.port-offset=100", "-Dkeycloak.import=/opt/jboss/keycloak/standalone/configuration/my-config.json"]