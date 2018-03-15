# Model sql

game(id* int, title text, type*->Type(id), price int);
package(id*, name text, price int);
package_game(package->package(id), game->game(id));
type(id* int, type text);
user(id* int, nickname varchar(20),name text, mail text, password varchar(20));
friend(user1*->user(id), user2*->user(id));
friend_request(claimant*->user(id), receiver*->user(id));
library(owner*->user(id), game*->game(id), buyer->user(id), active int);
review(author*->user(id), game*->game(id), mark int, review text);
offer_game(id*, game->game(id), pourcent int, date_debut date, date_fin date);
offer_package(id*, package->package(id), pourcent int, date_debut date, date_fin date);

Package c'est une compilation de jeux exemple Orange box sur steam.
Peut etre mettre un int en clef primaire pour library et review pour pas avoir de clef composée

# JAVA UML

La plupart des classe sql ont une classe java exemple:
Game(title text, type*->Type(id), price int);
elles ont toutes un constructeur sans l'id pour un crééer une nouvelle et des classe static pour interargir avec la DB -> add(), remove() etc
Peut etre faire une interface pour tout les truc qui interagisse avec la DB
elle ont toute des methode pour les serialiser en json

Il faut une classe en plus pour le panier

# Remarques
Pour le controler -> servlet
??? on fait vapeur.com/api/friend/1 pour recuperer tout les amis de 1 ou vapeur.com/app/friend pour recup tout les amis ?
je dirais la deuxieme et on gere la connexion avec des sessions.
Pour les servelt TOUT EN JSON
Review editable ou pas ?
Ou fait une feature pour voir les jeux de ses amis ?

# URL SERVLET

GET /user -> plusieur comportement en fonction de loggé ou pas et de amis ou pas
POST /Auth -> se log
POST /register -> s'inscrire
POST /passwordforgotten -> recevoir pass par mail

GET /friend -> liste d'amis de l'utilisateur
POST /friend -> ajoute un ami
DELETE /friend -> delete un ami

GET /user/friendrequest -> demandes amis
POST /user/friendrequest -> accepter un ami
DELETE /user/friendrequest -> refuser un ami
GET /user/library/active -> jeux actif
GET /user/library/inactive -> jeux non actif
POST /user/game/{id}/active -> activer un jeu
POST /user/game/{id}/buy -> acheter un jeu
POST /user/game/{id}/offer -> offrir un jeu
GET /user/review -> les review de l'utilisateur
GET /user/game/{id}/review -> la review de l'utilisateur
POST /user/game/{id}/review -> ajouter une review
GET /user/{id}/library -> library de l'user id

GET /library -> liste de nos jeux

GET /game/{id} -> caracteristique du jeu id
GET /game/{id}/review -> les reviews du jeu id
GET /package/{id} -> caracteristique du package id


GET /offer -> promotion du moment

Ce sont les urls de bases aprés on peut penser a d'autre chose style recuperer la moyenne des review d'un jeu etc
mais ça depend si on fait une requete pour ça ou si on le calcul en front.



# Idée
Pour le panier: SESSION ET COOKIE

# SECURITE
session -> login
mdp crypté -> md5
