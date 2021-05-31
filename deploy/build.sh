#!/bin/bash

# preparing

rm -rf ./app
rm -rf ../backend/target
mkdir -p ./app

#building frontend
cd ../frontend
npm run-script build

cp -r ./dist/* ../backend/public/

#building backend

cd ../backend
sbt clean dist

#moving binaries

cd ../deploy
cp ../backend/target/universal/backend-1.0.zip ./app/
cd ./app
unzip backend-1.0.zip
rm backend-1.0.zip
cd ../

#creating docker image

docker build -t minesweeper-webapp .