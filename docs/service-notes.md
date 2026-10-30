# Notes sur la couche Service

## A. Modes d'injection comparés

| Critère | Constructeur | Attribut |
|---|---|---|
| Champ final possible | Oui | Non |
| Dépendance visible dans la signature | Oui | Non, cachée dans le corps de la classe |
| Utilisable avec new hors conteneur sans dépendance null | Oui, le constructeur impose le repository | Non, le champ reste null |
| Signalé par SonarQube for IDE | Non | Oui (règle S6813) |

## B. Services

| Service | Dépendances injectées | Mode | Justification |
|---|---|---|---|
| AgenceServiceImpl | IAgenceRepository | Constructeur | Un seul repository, champ final, constructeur généré par Lombok. |
| ClientServiceImpl | IClientRepository | Constructeur | Idem. |
| ContratServiceImpl | IContratRepository | Constructeur | Idem. |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Idem. |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Idem. |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Idem. |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Idem, service en lecture seule. |
| ReservationServiceImpl | IReservationRepository | Constructeur | Idem. |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Idem. |

## C. Messages d'erreur

**Message 1.** Exception : NoSuchBeanDefinitionException, enveloppée dans une UnsatisfiedDependencyException. Le bean concerné est IContratService, demandé par ContratController. Causes probables : l'annotation @Service est absente de ContratServiceImpl, ou la classe est placée hors du package tn.esprit.autoloc et n'est donc pas détectée par le scan. Correction : ajouter @Service ou déplacer la classe dans le package racine.

**Message 2.** Exception : NoUniqueBeanDefinitionException. Deux beans, emailNotificateur et smsNotificateur, ont le même type. Première correction : poser @Primary sur la classe du bean à utiliser par défaut. Seconde correction : poser @Qualifier("emailNotificateur") sur le paramètre du constructeur de NotificationController.

**Message 3.** Dépendance circulaire entre clientServiceImpl et reservationServiceImpl. Avec l'injection par constructeur, aucun des deux beans ne peut être créé avant l'autre, donc le cycle ne peut pas être contourné. Piste de conception : extraire la logique commune dans un troisième service utilisé par les deux, ou faire dépendre l'un des deux directement du repository de l'autre.

## D. Anomalies SonarQube for IDE

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| | | |
| | | |
| | | |

## E. Questions de compréhension

1. Le new de ContratServiceImpl est exécuté par le conteneur Spring, créé par SpringApplication.run. Il détecte la classe grâce à @Service et lui fournit le proxy de IContratRepository par le constructeur.
2. Un contrôleur dépend de l'interface pour rester indépendant de l'implémentation : on peut la remplacer ou la simuler sans modifier le contrôleur.
3. Un singleton est partagé par tous les threads. Un attribut modifiable, comme un contrat courant mémorisé dans le service, serait écrasé par une requête concurrente : un utilisateur pourrait alors voir ou modifier le contrat d'un autre.
4. Passer directement l'objet reçu à save déclencherait un merge qui écrase toutes les colonnes, y compris celles non envoyées. Avec orphanRemoval, cela pourrait aussi vider la collection de paiements. On charge donc l'existant et on recopie uniquement les attributs modifiables.
5. @Component se pose sur la classe elle-même et la rend détectable par le scan. @Bean se pose sur une méthode d'une classe @Configuration, pour une classe qu'on ne peut pas annoter ou une construction particulière. @Primary désigne un candidat par défaut, posé sur la classe. @Qualifier choisit un bean par son nom au point d'injection. IPaiementService est en lecture seule parce que le paiement est l'enfant d'une composition : sa création et sa suppression passent par le Contrat, via la cascade et l'orphanRemoval.
