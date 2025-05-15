nginx settings `cat /etc/nginx/nginx.conf` includes scanning of active configurations: `include /etc/nginx/sites-enabled/*;`
In the catalog `/etc/nginx/sites-enabled/` if the default link to profile is: `ls -la /etc/nginx/sites-enabled/`
[Change it to our profile](https://unix.stackexchange.com/a/152000/216630):

```
cd /etc/nginx/sites-enabled/
sudo ln -sfn /opt/codegymjira/config/nginx.conf default
cat default
sudo service nginx reload (start/stop)
```