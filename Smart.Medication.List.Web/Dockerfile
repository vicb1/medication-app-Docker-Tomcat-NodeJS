FROM node:10

# Create app directory
WORKDIR /usr/src/app

# Copy tools directory for preinstall
COPY tools/ ./tools/

# Install app dependencies
# A wildcard is used to ensure both package.json AND package-lock.json are copied
# where available (npm@5+)
COPY package*.json ./

RUN npm install --unsafe-perm
# If you are building your code for production
# RUN npm install --only=production

# Bundle app source
COPY . .

ARG __API_URL__

ENV __API_URL__ $__API_URL__

EXPOSE 8080
CMD [ "npm", "run", "build" ]