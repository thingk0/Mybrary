# Stage 1: Build React App
FROM node:14 as build

WORKDIR /app

COPY package*.json ./
RUN npm install
COPY . .

RUN npm run build

# Stage 2: Serve React App with Nginx
FROM nginx:alpine

# Nginx 설정 파일 복사
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Build 된 React 앱 파일 복사
COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
