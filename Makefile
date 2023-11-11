frontImage:
	docker build --no-cache -t droidzed/devops-project-front:1.0.0 DevOps_Project_Front/

backImage:
	docker build --no-cache -t droidzed/aymendhahri-5sim3-g1-projet4-spring-angular:1.0.0 DevOps_Project/

compose:
	docker compose up -d

decompose:
	docker compose down

buildAndRun: frontImage backImage compose