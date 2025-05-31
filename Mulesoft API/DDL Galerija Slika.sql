-- Database: GalerijaSlika

--drop table if exists "TipSlike" cascade;
--drop table if exists "Slikar" cascade;
--drop table if exists "Potvrda" cascade;
--drop table if exists "Period" cascade;
--drop table if exists "Zaposleni" cascade;
--drop table if exists "Kupac" cascade;
--drop table if exists "Narudzbina" cascade;
--drop table if exists "Slika" cascade;

-- Create the table tblKupac
CREATE TABLE "Kupac" (
    "KupacID" int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "ImeKupca" varchar(20),
    "PrezimeKupca" varchar(20),
    "Adresa" varchar(50),
    "JMBG" varchar(13),
	"Pol" varchar(10) CHECK ("Pol" IN ('musko','zensko')),
    "KontaktKupca" varchar(15),
    "Datum_rodjenja" date,
	"Aktivan" boolean,
    "GotovinskoStanje" numeric(12, 2)
);

-- Create the table tblZaposleni
CREATE TABLE "Zaposleni" (
    "ZaposleniID" int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "ImeZaposlenog" varchar(10),
    "PrezimeZaposlenog" varchar(20),
    "Adresa" varchar(50),
    "JMBG" varchar(13),
    "KontaktZaposlenog" varchar(15),
    "Lozinka" varchar(16)
);

-- Create the table tblNarudzbina
CREATE TABLE "Narudzbina" (
    "NarudzbinaID" int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Datum" date,
    "Primarnost" varchar(1) CHECK ("Primarnost" IN ('A','B','C')),
     "Kolicina" integer,
    "ZaposleniID" integer,
    "KupacID" integer,
    FOREIGN KEY ("KupacID") REFERENCES "Kupac" ("KupacID"),
    FOREIGN KEY ("ZaposleniID") REFERENCES "Zaposleni" ("ZaposleniID")
);

-- Create the table tblPeriod
CREATE TABLE "Period" (
    "PeriodID" int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "NazivPerioda" varchar(20)
);

-- Create the table tblPotvrda
CREATE TABLE "Potvrda" (
    "PotvrdaID" int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "IzvrsenaTransakcija" boolean,
    "NarudzbinaID" integer,
    FOREIGN KEY ("NarudzbinaID") REFERENCES "Narudzbina" ("NarudzbinaID")
);

-- Create the table tblSlikar
CREATE TABLE "Slikar" (
    "SlikarID" int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "ImeSlikara" varchar(15),
    "PrezimeSlikara" varchar(15),
    "Nacionalnost" varchar(15)
);

-- Create the table tblTipSlike
CREATE TABLE "TipSlike" (
    "TipSlikeID" int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "NazivTipaSlike" varchar(25)
);

-- Create the table tblSlika
CREATE TABLE "Slika" (
    "SlikaID" int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Naziv_slike" varchar(30),
    "Cena" numeric(10, 2),
    "PeriodID" integer,
    "TipSlikeID" integer,
    "SlikarID" integer,
    "NarudzbinaID" integer,
    FOREIGN KEY ("PeriodID") REFERENCES "Period" ("PeriodID"),
    FOREIGN KEY ("SlikarID") REFERENCES "Slikar" ("SlikarID"),
    FOREIGN KEY ("TipSlikeID") REFERENCES "TipSlike" ("TipSlikeID"),
    FOREIGN KEY ("NarudzbinaID") REFERENCES "Narudzbina" ("NarudzbinaID")
);




