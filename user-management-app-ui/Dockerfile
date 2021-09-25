FROM nginx:1.21.3-alpine

MAINTAINER imousmoutis

COPY dist /usr/share/nginx/html

EXPOSE 80

RUN mv /usr/share/nginx/html/nginx_docker.conf /etc/nginx/conf.d/default.conf

CMD ["nginx", "-g", "daemon off;"]
