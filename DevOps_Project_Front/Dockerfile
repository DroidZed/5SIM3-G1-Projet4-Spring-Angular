FROM node:lts-bookworm-slim as build

WORKDIR /dist/src/app

RUN npm cache clean --force

COPY . .

RUN npm install

RUN npm run build --omit=dev

FROM nginx:latest AS ngi

COPY --from=build /dist/src/app/dist/summer-workshop-angular /usr/share/nginx/html

COPY /nginx.conf  /etc/nginx/conf.d/default.conf

EXPOSE 80