-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema Alimentation
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Alimentation
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Alimentation` DEFAULT CHARACTER SET utf8 ;
USE `Alimentation` ;

-- -----------------------------------------------------
-- Table `Alimentation`.`categorie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Alimentation`.`categorie` (
  `idCat` INT(3) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `nomCat` VARCHAR(60) NOT NULL UNIQUE,
  PRIMARY KEY (`idCat`))
	
ENGINE = InnoDB
AUTO_INCREMENT = 100
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `Alimentation`.`Client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Alimentation`.`Client` (
  `idClient` INT(7) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT 'Mettre comme valeur de départ 1 million',
  `nom` VARCHAR(100) NOT NULL,
  `tel` VARCHAR(13) NULL,
  `adresse` VARCHAR(45) NULL,
  `bonus` INT(10) UNSIGNED NULL DEFAULT 0,
  `actif` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1,
  PRIMARY KEY (`idClient`))
ENGINE = InnoDB
AUTO_INCREMENT = 1000000;


-- -----------------------------------------------------
-- Table `Alimentation`.`commande`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Alimentation`.`commande` (
  `idCommande` INT(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `livre` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0',
  `commentaires` VARCHAR(100) NULL DEFAULT 'ND',
  `idClient` INT(7) UNSIGNED NOT NULL,
  PRIMARY KEY (`idCommande`),
  INDEX `fk_commande_Client1_idx` (`idClient` ASC) ,
  CONSTRAINT `fk_commande_Client1`
    FOREIGN KEY (`idClient`)
    REFERENCES `Alimentation`.`Client` (`idClient`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `Alimentation`.`gestionnaire`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Alimentation`.`gestionnaire` (
  `idGest` INT(4) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT 'Maximum 4 chiffres',
  `nomGest` VARCHAR(45) NOT NULL,
  `typeGest` TINYINT(1) UNSIGNED NOT NULL,
  `login` VARCHAR(20) NOT NULL,
  `pwd` VARCHAR(100) NOT NULL,
  `actif` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0',
  PRIMARY KEY (`idGest`),
  UNIQUE INDEX `login` (`login` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 1000
DEFAULT CHARACTER SET = utf8
ROW_FORMAT = COMPACT;


-- -----------------------------------------------------
-- Table `Alimentation`.`facture`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Alimentation`.`facture` (
  `idFac` INT(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT 'Démarrer à 4 millions\n',
  `dateFac` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `codePaiement` VARCHAR(60) NOT NULL DEFAULT '000000',
  `remise` DECIMAL(4,2) UNSIGNED NOT NULL DEFAULT 0,
  `montant` DECIMAL(10,2) UNSIGNED NOT NULL DEFAULT 0,
  `modePaiement` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0',
  `idCaissiere` INT(4) UNSIGNED ZEROFILL NOT NULL,
  `idClient` INT(7) ZEROFILL UNSIGNED NOT NULL,
  `idCommande` INT(10) UNSIGNED ZEROFILL NULL,
  PRIMARY KEY (`idFac`),
  INDEX `fk_idCaiss_idx` (`idCaissiere` ASC) ,
  INDEX `fk_facture_Client1_idx` (`idClient` ASC) ,
  INDEX `fk_facture_commande1_idx` (`idCommande` ASC),
  CONSTRAINT `fk_idCaiss`
    FOREIGN KEY (`idCaissiere`)
    REFERENCES `Alimentation`.`gestionnaire` (`idGest`)
    ON UPDATE CASCADE,
  CONSTRAINT `fk_facture_Client1`
    FOREIGN KEY (`idClient`)
    REFERENCES `Alimentation`.`Client` (`idClient`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_facture_commande1`
    FOREIGN KEY (`idCommande`)
    REFERENCES `Alimentation`.`commande` (`idCommande`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4000000
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `Alimentation`.`produit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Alimentation`.`produit` (
  `codePro` INT(6) UNSIGNED NOT NULL COMMENT 'A générer aléatoirement',
  `nomPro` VARCHAR(100) NOT NULL,
  `prixVente` DECIMAL(8,2) UNSIGNED NOT NULL,
  `prixAchat` DECIMAL(8,2) UNSIGNED NOT NULL,
  `qte` DECIMAL(8,2) UNSIGNED NOT NULL,
  `description` VARCHAR(1000) NOT NULL DEFAULT 'RAS',
  `codeFour` VARCHAR(40) NOT NULL,
  `dateInsertion` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `actif` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1',
  `id_Categorie` INT(3) UNSIGNED NULL,
  PRIMARY KEY (`codePro`),
  INDEX `fk_produit_categorie1_idx` (`id_Categorie` ASC) ,
  UNIQUE INDEX `nomPro_UNIQUE` (`nomPro` ASC) ,
  CONSTRAINT `fk_produit_categorie1`
    FOREIGN KEY (`id_Categorie`)
    REFERENCES `Alimentation`.`categorie` (`idCat`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `Alimentation`.`gestionstock`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Alimentation`.`gestionstock` (
  `idStock` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `qte` DECIMAL(8,2) UNSIGNED NOT NULL,
  `dateStock` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `operation` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0',
  `idGest` INT(4) UNSIGNED NOT NULL,
  `codePro` INT(6) UNSIGNED NOT NULL,
  PRIMARY KEY (`idStock`),
  INDEX `fk_idGest_stock_idx` (`idGest` ASC) ,
  INDEX `fk_codePro_stock_idx` (`codePro` ASC) ,
  CONSTRAINT `fk_codePro_stock`
    FOREIGN KEY (`codePro`)
    REFERENCES `Alimentation`.`produit` (`codePro`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_idGest_stock`
    FOREIGN KEY (`idGest`)
    REFERENCES `Alimentation`.`gestionnaire` (`idGest`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `Alimentation`.`lignecommande`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Alimentation`.`lignecommande` (
  `codePro` INT(6) UNSIGNED NOT NULL,
  `idCommande` INT(10) UNSIGNED NOT NULL,
  `qte` DECIMAL(8,2) UNSIGNED NOT NULL,
  INDEX `fk_IDCOMMANDE` (`idCommande` ASC) ,
  INDEX `fk_CODEPRODUIT` (`codePro` ASC) ,
  PRIMARY KEY (`codePro`, `idCommande`),
  CONSTRAINT `fk_CODEPRODUIT`
    FOREIGN KEY (`codePro`)
    REFERENCES `Alimentation`.`produit` (`codePro`)
    ON UPDATE CASCADE,
  CONSTRAINT `fk_IDCOMMANDE`
    FOREIGN KEY (`idCommande`)
    REFERENCES `Alimentation`.`commande` (`idCommande`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 28
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `Alimentation`.`lignefacture`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Alimentation`.`lignefacture` (
  `codePro` INT(6) UNSIGNED ZEROFILL NOT NULL,
  `idFac` INT(10) UNSIGNED ZEROFILL NOT NULL,
  `prixVente` DECIMAL(8,2) UNSIGNED NOT NULL COMMENT 'Il s\'agit du prix unitaire du produit au moment de la vente',
  `qte` DECIMAL(8,2) UNSIGNED NOT NULL,
  `prixAchat` DECIMAL(8,2) UNSIGNED NOT NULL COMMENT 'Il s\'agiy du prix d\'achat au moment de la vente',
  INDEX `fk_idFac` (`idFac` ASC) ,
  PRIMARY KEY (`codePro`, `idFac`),
  CONSTRAINT `fk_codePro`
    FOREIGN KEY (`codePro`)
    REFERENCES `Alimentation`.`produit` (`codePro`)
    ON UPDATE CASCADE,
  CONSTRAINT `fk_idFac`
    FOREIGN KEY (`idFac`)
    REFERENCES `Alimentation`.`facture` (`idFac`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `Alimentation`.`Fournisseur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Alimentation`.`Fournisseur` (
  `idFournisseur` INT(3) ZEROFILL UNSIGNED NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(45) NOT NULL,
  `adresse` VARCHAR(45) NULL,
  `tel` VARCHAR(45) NULL,
  PRIMARY KEY (`idFournisseur`),
  UNIQUE INDEX `nom_UNIQUE` (`nom` ASC) )
ENGINE = InnoDB;

DELIMITER |
CREATE TRIGGER before_delete_categorie BEFORE DELETE ON categorie FOR EACH ROW
BEGIN
	DECLARE id INT(3) UNSIGNED;
	DECLARE fin TINYINT DEFAULT 0;
	DECLARE curs_categorie CURSOR
		FOR SELECT codePro FROM produit WHERE id_Categorie = OLD.idCat;
	
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin = 1;

	OPEN curs_categorie;

	loop_curseur : LOOP
		FETCH curs_categorie INTO id;
		UPDATE produit
		SET id_Categorie = 100
		WHERE codePro = id;
		IF fin = 1 THEN
			LEAVE loop_curseur;
		END IF;
	END LOOP;

	CLOSE curs_categorie;
END|
DELIMITER ;

DELIMITER |
CREATE TRIGGER after_insert_facture AFTER INSERT ON Facture FOR EACH ROW
BEGIN
	SELECT NEW.idCommande INTO @commande;
	IF @commande IS NOT NULL THEN
		UPDATE commande
		SET livre = TRUE
		WHERE idCommande = @commande;
	END IF;
END|
DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `Alimentation`.`categorie`
-- -----------------------------------------------------
START TRANSACTION;
USE `Alimentation`;
INSERT INTO `Alimentation`.`categorie` (`idCat`, `nomCat`) VALUES (000, 'ND');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Alimentation`.`Client`
-- -----------------------------------------------------
START TRANSACTION;
USE `Alimentation`;
INSERT INTO `Alimentation`.`Client` (`idClient`, `nom`, `tel`, `adresse`, `bonus`, `actif`) VALUES (0000000, 'ND', NULL, NULL, NULL, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Alimentation`.`Fournisseur`
-- -----------------------------------------------------
START TRANSACTION;
USE `Alimentation`;
INSERT INTO `Alimentation`.`Fournisseur` (`idFournisseur`, `nom`, `adresse`, `tel`) VALUES (000, 'ND', NULL, NULL);

COMMIT;

