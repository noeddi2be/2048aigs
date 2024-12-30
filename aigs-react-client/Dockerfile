# Frontend Dockerfile
FROM node:18-alpine AS build
WORKDIR /aigs-react-client

COPY package*.json ./

RUN npm cache clean --force && npm install

COPY . .
RUN npm run build

FROM node:18-alpine
WORKDIR /aigs-react-client
COPY --from=build /aigs-react-client ./

EXPOSE 3000
CMD ["npm", "start"]