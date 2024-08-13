# Année 1 - SAE 1.02
Projet E3Cète - SAE 1.02 | Année 1 | IUT Montpellier-Sète

# Descriptif
**Sujet** : On s'intéresse au jeu de cartes "**E3Cète**", jouable à un seul joueur (par un humain ou un ordinateur) : l'objectif est de réaliser des ensembles de trois _Cartes_, notés _E3C_. Une _Carte_ possède 3 caractéristiques : une _Couleur_, une _Texture_ et une _Figure_ répétée un certain nombre de fois. Un ensemble de trois _Cartes_ est un _E3C_ si et seulement si pour chaque caractéristique, l'ensemble des trois _Cartes_ doivent être toutes égales ou toutes différentes. Le jeu est affiché sous forme de _Table_ matricielle, permettant de stocker 9 cartes faces visibles piochées sur le dessus d'un _Paquet_ de _Cartes_, jouable par le biais d'un terminal/[shell](https://fr.wikipedia.org/wiki/Bourne-Again_shell) (avec un affichage en couleur pour une meilleure expérience utilisateur).

Le travail est segmenté en trois parties :
- **Modélisation et comparaison d'algorithmes de tris** (5 points) : Implémentation simpliste des différents éléments d'un jeu d'E3Cète et de différents algorithmes de tris ([Sélection](https://fr.wikipedia.org/wiki/Tri_par_s%C3%A9lection), [Insertion](https://fr.wikipedia.org/wiki/Tri_par_insertion) et [Bulles](https://fr.wikipedia.org/wiki/Tri_%C3%A0_bulles)) afin de pouvoir les comparer et déterminer le plus efficace via des graphiques. L'étude des différents algorithmes est disponible en [pdf](tex/SAE1.02-Maths-FRANCEUS_COINTREL-RENAUD.pdf).
- **Jeu E3Cète de base** (10 points) : Implémentation complète des différents éléments d'un jeu d'E3Cète, pouvant être jouable.
- **Jeu E3Cète avancé** (5 points; version de base + extensions) : Implémentation de nouvelles fonctionnalités améliorant la qualité et la complexité du jeu, il y a un total de 5 extensions.

L'ensemble du sujet est disponible en [pdf](SAE_1_02_E3Cete-V4bis.pdf).

# Extensions
- [x] 3.1/ Fin de partie (1 pt)
- [x] 3.2/ Saisie simplifiée pour l'utilisateur (1 pt)
- [x] 3.3/ Ensemble de x Cartes : ExC (1 pt)
- [x] 3.4/ Taille de la table (1 pt)
- [ ] 3.5/ Contre la montre (1 pt)
  - [ ] Version 1
  - [ ] Version 2

# Membres du groupe
- Milwenn FRANCEUS--COINTREL
- Julien RENAUD

___
_**Remarque** : Les fonctions liées à la partie Mathématiques sont à la fin de la classe **Paquet**._