# RealStateMarketPlace

download key cloak image:

https://drive.google.com/file/d/1ImlupbX6tiqbfJLG6BhyC5oVA-VkaxLQ/view?usp=sharing

To build the image for keycloak run the following comand in the root folder of the application : 

docker load -i meu-keycloak-configurado.tar

To run the docker compose with the postgree Database and keycloak, please run inside the folder /RealState : 

docker compose up -d

You will need to update the client secret env in the file application.properties inside the folder \RealState\src\main\resources\