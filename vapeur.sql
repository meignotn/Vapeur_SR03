delete ignore from review;
delete ignore from  library;
delete ignore from friend_request;
delete ignore from friend;
delete ignore from  user;
delete ignore from  game;
delete ignore from type;
DROP TABLE IF EXISTS offer_game;
DROP TABLE IF EXISTS offer_package;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS library;
DROP TABLE IF EXISTS friend_request;
DROP TABLE IF EXISTS friend;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS package_game;
DROP TABLE IF EXISTS package;
DROP TABLE IF EXISTS game;
DROP TABLE IF EXISTS type;

CREATE TABLE IF NOT EXISTS type(id int PRIMARY KEY,type varchar(20));
CREATE TABLE IF NOT EXISTS game(id serial,title varchar(200),description varchar(1000),image varchar(20), type int, price int, PRIMARY KEY (id),FOREIGN KEY (type) REFERENCES type(id));
CREATE TABLE IF NOT EXISTS user(id serial,nickname varchar(20),name varchar(200),mail varchar(200),wallet int default 0,password varchar(200),PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS friend(id serial, user1 BIGINT UNSIGNED,user2 BIGINT UNSIGNED,PRIMARY KEY (id),FOREIGN KEY (user1) REFERENCES user(id),FOREIGN KEY (user1) REFERENCES user(id));
CREATE TABLE IF NOT EXISTS friend_request(id serial, claimant BIGINT UNSIGNED,receiver BIGINT UNSIGNED,PRIMARY KEY (id),FOREIGN KEY (claimant) REFERENCES user(id),FOREIGN KEY (receiver) REFERENCES user(id));
CREATE TABLE IF NOT EXISTS library(id  serial, owner BIGINT UNSIGNED,game BIGINT UNSIGNED,buyer BIGINT UNSIGNED,active int,PRIMARY KEY (id),FOREIGN KEY (owner) REFERENCES user(id),FOREIGN KEY (game) REFERENCES game(id),FOREIGN KEY (buyer) REFERENCES user(id));
CREATE TABLE IF NOT EXISTS review(id serial, author BIGINT UNSIGNED,game BIGINT UNSIGNED,mark int,review varchar(200),PRIMARY KEY (id),FOREIGN KEY (author) REFERENCES user(id),FOREIGN KEY (game) REFERENCES game(id));

INSERT INTO type(id,type) values (0, "PC");
INSERT INTO type(id,type) values (1, "PS4");
INSERT INTO type(id,type) values (2, "XBOX");

INSERT INTO game(title, type, price, description, image) values ("Counter-Strike:Global Offensive", (select id from type where type="PC"),10, "Counter-Strike: Global Offensive (CS:GO) expands upon the team-based action gameplay that the franchise pioneered when it was launched 12 years ago.CS:GO features new maps, characters, and weapons and delivers updated versions of the classic CS content (de_dust, etc.). In addition, CS:GO introduces new gameplay modes, matchmaking, leader boards, and more.", "csgo.jpg");

INSERT INTO game(title, type, price, description, image) values ("Counter-Strike:1.6", (select id from type where type="PC"),5,"Originally a modification for Half-Life, the rights to Counter-Strike, as well as the developers working on it, were acquired by Valve Corporation in 2000.","cs.jpg");

INSERT INTO game(title, type, price, description, image) values ("Dishonored", (select id from type where type="PS4"),30," Set in the fictional, plague-ridden industrial city of Dunwall, Dishonored follows the story of Corvo Attano, bodyguard to the Empress of the Isles. He is framed for her murder and forced to become an assassin, seeking revenge on those who conspired against him. Corvo is aided in his quest by the Loyalists—a resistance group fighting to reclaim Dunwall, and the Outsider—a powerful being who imbues Corvo with magical abilities.","dishonored.jpg");

INSERT INTO game(title, type, price, description, image) values ("Dishonored 2", (select id from type where type="XBOX"),60,"Play your way in a world where mysticism and industry collide. Will you choose to play as Empress Emily Kaldwin or the royal protector, Corvo Attano? Will you make your way through the game unseen, make full use of its brutal combat system, or use a blend of both? How will you combine your character's unique set of powers, weapons and gadgets to eliminate your enemies? The story responds to your choices, leading to intriguing outcomes, as you play through each of the game's hand-crafted missions.","dishonored2.jpg");

INSERT INTO game(title, type, price, description, image) values ("Portal", (select id from type where type="PC"),10,"Portal consists primarily of a series of puzzles that must be solved by teleporting the player's character and simple objects using \"the Aperture Science Handheld Portal Device\", a device that can create inter-spatial portals between two flat planes. The player-character, Chell, is challenged and taunted by an artificial intelligence named GLaDOS (Genetic Lifeform and Disk Operating System) to complete each puzzle in the Aperture Science Enrichment Center using the portal gun with the promise of receiving cake when all the puzzles are completed. The game's unique physics allows momentum to be retained through portals, requiring creative use of portals to maneuver through the test chambers. This gameplay element is based on a similar concept from the game Narbacular Drop; many of the team members from the DigiPen Institute of Technology who worked on Narbacular Drop were hired by Valve for the creation of Portal, making it a spiritual successor to the game.","portal.png");

INSERT INTO game(title, type, price, description, image) values ("Portal 2", (select id from type where type="PC"),30,"The game retains Portal's gameplay elements, and adds new features, including tractor beams, laser redirection, bridges made of light, and paint-like 'gels' accelerating the player's speed, allowing the player-character to jump higher or place portals on any surface. These gels were created by the team from the Independent Games Festival-winning DigiPen student project Tag: The Power of Paint. In the single-player campaign, the player controls protagonist Chell, awoken from suspended animation after many years, who must navigate the now-dilapidated Aperture Science Enrichment Center during its reconstruction by the reactivated GLaDOS, a powerful supercomputer. The storyline introduces new characters, including Wheatley (Stephen Merchant) and Cave Johnson (J. K. Simmons).","portal2.jpg");

INSERT INTO game(title, type, price, description, image) values ("The secret of Monkey Island", (select id from type where type="PC"),20,"The Secret of Monkey Island is a 1990 point-and-click graphic adventure game developed and published by Lucasfilm Games. It takes place in a fantastic version of the Caribbean during the age of piracy. The player assumes the role of Guybrush Threepwood, a young man who dreams of becoming a pirate and explores fictional islands while solving puzzles.","MI.jpg");

INSERT INTO game(title, type, price, description, image) values ("Monkey Island 2: LeChuck's Revenge", (select id from type where type="PC"),20,"Monkey Island 2: LeChuck's Revenge is an adventure game developed and published by LucasArts in 1991. It was the second game of the Monkey Island series, following The Secret of Monkey Island, and the sixth LucasArts game to use the SCUMM engine. It was the first game to use the iMUSE sound system.\nThe game's story centers on the wannabe pirate Guybrush Threepwood. After defeating ghost pirate LeChuck in The Secret of Monkey Island, little is known of what happened between Guybrush Threepwood and Elaine Marley. The sequel involves Guybrush's attempts to find the mysterious treasure of Big Whoop.","MI2.jpg");
INSERT INTO game(title, type, price, description, image) values ("Metro 2033", (select id from type where type="PC"),15,"Metro 2033 est un jeu video de tir en vue a la premiere personne post-apocalyptique et un survival horror developpe par 4A Games et edite par THQ. Il est sorti sur PC et Xbox 360 le 18 mars 2010, apres un developpement d'environ quatre ans. Il s'agit du premier jeu de la serie.","metro.jpg");
INSERT INTO game(title, type, price, description, image) values ("Metro Last Light", (select id from type where type="PC"),40,"Un an apres les evenements de Metro 2033, les Rangers, dont Artyom fait desormais partie, ont pris place dans le complexe militaire de D6. Cet immense bunker construit avant la guerre contient un grand nombre de ressources et de longs tunnels dont l'exploration n'est toujours pas terminee. Une rumeur revelant les richesses que D6 recele s'est repandue dans le Metro, ravivant les tensions entre les differentes factions qui l'occupent","metro2.jpg");



INSERT INTO user(nickname, name, mail, password) values ("Buzz", "Buzz Leclair", "buzz@pizzaplanet.com", "5f4dcc3b5aa765d61d8327deb882cf99");

INSERT INTO review (author,game,mark,review) values ((select id from user where nickname="Buzz"), 1, 1, "Praesent tincidunt sed tellus ut rutrum. Sed vitae justo condimentum, porta lectus vitae, ultricies congue gravida diam non fringilla.");
INSERT INTO review (author,game,mark,review) values ((select id from user where nickname="Buzz"), 1, 2, "Praesent tincidunt sed tellus ut rutrum. Sed vitae justo condimentum, porta lectus vitae, ultricies congue gravida diam non fringilla.");
INSERT INTO review (author,game,mark,review) values ((select id from user where nickname="Buzz"), 1, 3, "Praesent tincidunt sed tellus ut rutrum. Sed vitae justo condimentum, porta lectus vitae, ultricies congue gravida diam non fringilla.");
INSERT INTO review (author,game,mark,review) values ((select id from user where nickname="Buzz"), 1, 4, "Praesent tincidunt sed tellus ut rutrum. Sed vitae justo condimentum, porta lectus vitae, ultricies congue gravida diam non fringilla.");
INSERT INTO review (author,game,mark,review) values ((select id from user where nickname="Buzz"), 1, 5, "Praesent tincidunt sed tellus ut rutrum. Sed vitae justo condimentum, porta lectus vitae, ultricies congue gravida diam non fringilla.");
INSERT INTO review (author,game,mark,review) values ((select id from user where nickname="Buzz"), 1, 6, "Praesent tincidunt sed tellus ut rutrum. Sed vitae justo condimentum, porta lectus vitae, ultricies congue gravida diam non fringilla.");
INSERT INTO review (author,game,mark,review) values ((select id from user where nickname="Buzz"), 1, 7, "Praesent tincidunt sed tellus ut rutrum. Sed vitae justo condimentum, porta lectus vitae, ultricies congue gravida diam non fringilla.");
INSERT INTO review (author,game,mark,review) values ((select id from user where nickname="Buzz"), 1, 8, "Praesent tincidunt sed tellus ut rutrum. Sed vitae justo condimentum, porta lectus vitae, ultricies congue gravida diam non fringilla.");
INSERT INTO review (author,game,mark,review) values ((select id from user where nickname="Buzz"), 1, 9, "Praesent tincidunt sed tellus ut rutrum. Sed vitae justo condimentum, porta lectus vitae, ultricies congue gravida diam non fringilla.");
INSERT INTO review (author,game,mark,review) values ((select id from user where nickname="Buzz"), 1, 10, "Praesent tincidunt sed tellus ut rutrum. Sed vitae justo condimentum, porta lectus vitae, ultricies congue gravida diam non fringilla.");


