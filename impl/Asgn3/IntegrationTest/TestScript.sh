#! /bin/bash

mkdir ./DirectoryA
mkdir ./DirectoryB

#Starting the two Dropbox instances
nohup java -jar ./Dropbox.jar ./DirectoryA > ./DropboxA.log &
sleep 10

ip_=$(ifconfig | grep 'inet addr:' | grep -v '127.0.0.1' | cut -d: -f2 | awk '{ print $1}')
nohup java -jar ./Dropbox.jar ./DirectoryB $ip_ > ./DropboxB.log &
sleep 10

testExists () {
    if [ -e $1 ]
    then
        echo $1 exists
    else
        echo $1 does not exist
    fi
}

testEquals () {
    if cmp -s $1 $2
    then
        echo $1 is the same as $2
    else
        echo $1 is not the same as $2
    fi
}

#Begining testing

#create A -> B
echo message > ./DirectoryA/FileA.txt
sleep 5
testExists ./DirectoryB/FileA.txt
testEquals ./DirectoryA/FileA.txt ./DirectoryB/FileA.txt

#modify A -> B
echo newmessage > ./DirectoryA/FileA.txt
sleep 2
testEquals ./DirectoryA/FileA.txt ./DirectoryB/FileA.txt

#modify B -> A
echo anothernewmessage > ./DirectoryB/FileA.txt
sleep 2
testEquals ./DirectoryA/FileA.txt ./DirectoryB/FileA.txt

#create B -> A
echo message > ./DirectoryB/FileB.txt
sleep 2
testExists ./DirectoryA/FileB.txt
testEquals ./DirectoryA/FileB.txt ./DirectoryB/FileB.txt

#delete A -> B
rm -rf ./DirectoryA/FileA.txt
sleep 2
testExists ./DirectoryB/FileA.txt

#delete B -> A
rm -rf ./DirectoryB/FileB.txt
sleep 2
testExists ./DirectoryA/FileB.txt


pkill java 
rm -rf ./DirectoryA
rm -rf ./DirectoryB