### Deploy postgres in docker

`sudo apt update`

#### [Installing and testing docker](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-20-04)

`sudo apt install docker.io`

or

`sudo apt install apt-transport-https ca-certificates curl software-properties-common`  
`curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -`  
`sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable"`  
`sudo apt install docker-ce` - installation

----

`sudo systemctl status docker` - testing

#### [Launch postgres in docker](https://hub.docker.com/_/postgres)

`sudo docker pull postgres` - Install postgres image from docker hub repository

  ```
  sudo docker run \
	-p 5432:5432 \
	--name postgres-db \
	-e POSTGRES_USER=jira \
	-e POSTGRES_PASSWORD=CodeGymJira \
	-e POSTGRES_DB=jira \
	-e PGDATA=/var/lib/postgresql/data/pgdata \
	-v ./pgdata:/var/lib/postgresql/data \
	-d postgres
  ```

-- Launch postgres in docker container, where:

  ```
  -p 5432:5432 - port, on which the database will launch
  --name postgres-db - name of the docker container
  -e POSTGRES_PASSWORD=CodeGymJira - database password
  -e POSTGRES_USER=jira - database user's username
  -e POSTGRES_DB=jira - name of the database
  -e PGDATA=/var/lib/postgresql/data/pgdata - folder where data will be stored
  ```

`sudo docker update --restart unless-stopped postgres-db` - auto-launch the container after server restart

#### Credentials for connecting to database

  ```
  1. host - server ip-address
  2. port - 5432
  3. username - jira
  4. password - CodeGymJira
  5. table - jira
  ```


