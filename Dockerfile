## Builder to hide credentials from image
FROM alpine:latest AS builder

RUN apk add curl

ARG API_KEY
RUN curl -H "apikey: $API_KEY"\
    "https://sandbox.api.sap.com/s4hanacloud/sap/opu/odata/sap/API_BUSINESS_PARTNER/\$metadata"\
    -o "API_BUSINESS_PARTNER.edmx"

## Node.js app
FROM node:8

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm install

COPY . .

COPY --from=builder API_BUSINESS_PARTNER.edmx business-partner/

ENV PORT 8080
EXPOSE 8080
CMD [ "npm", "start" ]