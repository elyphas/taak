#!/bin/bash
nombre=arangodb
nombrebackup=$nombre-$(date +%d%B%Y-%H%M)

arangodump --server.database "example" --output-directory $nombrebackup

tar -cf $nombrebackup".tar" $nombrebackup

7z a $nombrebackup $nombrebackup.tar

sudo rm $nombrebackup.tar

sudo rm -r $nombrebackup

echo " "
echo " "
echo "Se respaldo en: "$nombrebackup".7z"
