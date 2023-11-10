# Stage 1
FROM node:lts-bookworm-slim as node
WORKDIR /app
COPY . .
RUN npm ci
RUN npm run build --prod
# Stage 2
FROM nginx:alpine
COPY --from=node /app/dist/angular-app /usr/share/nginx/html