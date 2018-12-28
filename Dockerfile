## Build docker image of mock server by passing in "--build-arg API_KEY=<API key from SAP API Business Hub>"
## ----

## Builder to hide credentials from image
FROM alpine:latest AS builder

RUN apk add curl libxml2-utils

ARG API_KEY
RUN curl -H "apikey: $API_KEY"\
    "https://sandbox.api.sap.com/s4hanacloud/sap/opu/odata/sap/API_BUSINESS_PARTNER/\$metadata"\
    | xmllint --format - > API_BUSINESS_PARTNER.edmx

COPY business-partner/API_BUSINESS_PARTNER.patch .
RUN patch API_BUSINESS_PARTNER.edmx API_BUSINESS_PARTNER.patch

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
