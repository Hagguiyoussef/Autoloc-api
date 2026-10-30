# Notes sur la couche Repository

## Choix des interfaces

| Interface | Étend | Justification |
|---|---|---|
| IContratRepository | JpaRepository<Contrat, Long> | CRUD complet, findAll renvoie une List, saveAndFlush disponible. |
| IPaiementRepository | JpaRepository<Paiement, Long> | Lecture des paiements, leur création et leur retrait passent par le Contrat. |
| IAgenceRepository | JpaRepository<Agence, Long> | CRUD complet, listes, tri et pagination. |
| IClientRepository | JpaRepository<Client, Long> | CRUD complet, listes, tri et pagination. |
| IEmployeRepository | JpaRepository<Employe, Long> | CRUD complet, listes, tri et pagination. |
| IEquipementRepository | JpaRepository<Equipement, Long> | CRUD complet, listes, tri et pagination. |
| IMaintenanceRepository | JpaRepository<Maintenance, Long> | CRUD complet, listes, tri et pagination. |
| IReservationRepository | JpaRepository<Reservation, Long> | CRUD complet, listes, tri et pagination. |
| IVehiculeRepository | JpaRepository<Vehicule, Long> | CRUD complet, listes, tri et pagination. |

## Anomalies SonarQube for IDE

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| | | |
| | | |
| | | |
