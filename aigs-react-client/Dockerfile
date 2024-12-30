# Frontend Dockerfile
FROM node:18-alpine AS build
WORKDIR /aigs-react-client
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM node:18-alpine
WORKDIR /aigs-react-client
COPY --from=build /aigs-react-client/.next ./.next
COPY --from=build /aigs-react-client/node_modules ./node_modules
COPY --from=build /aigs-react-client/package*.json ./

EXPOSE 3000
CMD ["npm", "start"]