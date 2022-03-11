-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-20
INSERT INTO users (id,user_name,"password",name,age,user_type) VALUES
	 (1,'admin','$2a$12$GQX1oFO7dQIUHtDrS2/pNuYRfdfgt.00MWC7YzARUOSVT5Swusg1G','admin',999,'admin'),
	 (2,'student','$2a$12$iDPdhEo8ewcqwqagAVjYJ.SMES4piBWmusiZ76uoR.vKCI1aceYBW','Slavik',26,'student'),
	 (3,'teacher','$2a$12$8RWIkuNE37xVh3Qj6qPZpOAENv1FPaffdtXpSNGrWJxCoe.PVk32q','Ychitel',12,'teacher'),
	 (4,'teacher12','$2a$12$fRb6vuqoZlPp2bdWXd4TZ.Fj./Dx5Or47AU9xJc/TKlxZpy8YYG1u','kek',12,'teacher'),
	 (5,'qwe453','$2a$12$PGIDIEUTp2M/aoQ.NF7blO9th24vNu1YbamXdoZwy9iWinqHTMMH6','qwe',123,'student'),
	 (6,'teacher1','$2a$12$3sntQS7oN3j/kLxHGtrUSOgXvd6USe3awF9.nLlTpgswWVSjjqHCG','Cheburator',124,'teacher')
	 ON CONFLICT DO NOTHING;