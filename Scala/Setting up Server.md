- Run ssh-keygen #Default type (-t) is rsa.
- Enter a secure passphrase (cannot be recovered)
===================================
sudo apt-get install -y git htop curl wget



Ec2 - install Java, mysql


#============== Java ===============
wget --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u60-b27/jdk-8u60-linux-x64.tar.gz && \
sudo mkdir -p /opt/jdk && \
sudo tar -zxf jdk-8u60-linux-x64.tar.gz -C /opt/jdk && \
rm -fr jdk-8u60-linux-x64.tar.gz && \
sudo update-alternatives --install /usr/bin/java java /opt/jdk/jdk1.8.0_60/bin/java 1086 && \
sudo update-alternatives --install /usr/bin/javac javac /opt/jdk/jdk1.8.0_60/bin/javac 1086 && \
sudo apt-get purge openjdk-7-jre openjdk-6-jre openjdk-6-jre-headless && \
sudo apt-get autoremove 
#============== Ant ================
wget http://www.us.apache.org/dist/ant/binaries/apache-ant-1.9.6-bin.tar.gz
tar xvfz apache-ant-1.9.6-bin.tar.gz
sudo mv apache-ant-1.9.6 /opt
vim ~/.profile and add ==> 
#======== Ant PATH ================
ANT_HOME="/opt/apache-ant-1.9.6"
PATH="$PATH:$ANT_HOME/bin"

#============== Scala ==============
wget www.scala-lang.org/files/archive/scala-2.11.7.deb
sudo dpkg -i scala-2.11.7.deb

#=============== SBT ===============
echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 642AC823
sudo apt-get update
sudo apt-get install sbt
// Run sbt to download dependencies.


