# PHP-CGI install

## For Linux derivatives

### Ubuntu 

```shell
$ sudo apt install php-cgi  # for phpjavabridge only php-cgi
```

Alternatively for newer versions, install the following repository:

```shell
$ sudo add-apt-repository -y ppa:ondrej/php;
$ sudo apt-get update;
```

Based on this repo you can install, for example :

```shell
$ sudo apt-get install php7.1-cgi php7.1-cli \
       php7.1-bcmath php7.1-mbstring php7.1-pgsql \
       php7.1-zip php7.1-json php7.1-mysql \
       php7.1-bz2 php7.1-curl php7.1-gmp \
       php7.1-ldap php7.1-sqlite3 \
       php7.1-gd php7.1-intl php7.1-opcache php-imagick php-memcached;
```

