--PERIOD
insert into "Period"("NazivPerioda")
values('Renesansa');

insert into "Period"("NazivPerioda")
values('Barok');

insert into "Period"("NazivPerioda")
values('Klasicizam');

insert into "Period"("NazivPerioda")
values('Romantizam');

insert into "Period"("NazivPerioda")
values('Realizam');


--SLIKAR
insert into "Slikar"("ImeSlikara","PrezimeSlikara","Nacionalnost")
values('Leonardo', 'da Vinci', 'Italija');

insert into "Slikar"("ImeSlikara","PrezimeSlikara","Nacionalnost")
values('Sandro', 'Botticelli', 'Italija');

insert into "Slikar"("ImeSlikara","PrezimeSlikara","Nacionalnost")
values('Peter Paul', 'Rubens', 'Belgija');

insert into "Slikar"("ImeSlikara","PrezimeSlikara","Nacionalnost")
values('Franciso', 'Goya', 'Španija');

insert into "Slikar"("ImeSlikara","PrezimeSlikara","Nacionalnost")
values('Pavel', 'Jovanović', 'Srbija');

--Tip Slike

insert into "TipSlike"("NazivTipaSlike")
values('Akvarel');

insert into "TipSlike"("NazivTipaSlike")
values('Uljana slika');

insert into "TipSlike"("NazivTipaSlike")
values('Akrilne boje');

insert into "TipSlike"("NazivTipaSlike")
values('Tempera');

insert into "TipSlike"("NazivTipaSlike")
values('Kolaž');





--Zaposleni
insert into "Zaposleni"("ImeZaposlenog","PrezimeZaposlenog","Adresa","JMBG","KontaktZaposlenog","Lozinka")
values('Milan', 'Pavlović', 'Miloša Obilića 10', '1203997184532','0645898654','0123uz98');

insert into "Zaposleni"("ImeZaposlenog","PrezimeZaposlenog","Adresa","JMBG","KontaktZaposlenog","Lozinka")
values('Dunja', 'Popović', 'Cara Lazara 20', '1203998188534','0640008114','8726wt71');

insert into "Zaposleni"("ImeZaposlenog","PrezimeZaposlenog","Adresa","JMBG","KontaktZaposlenog","Lozinka")
values('Lazar', 'Kojić', 'Miloša Obilića 21', '2107987184632','0645111222','5142qw98');

insert into "Zaposleni"("ImeZaposlenog","PrezimeZaposlenog","Adresa","JMBG","KontaktZaposlenog","Lozinka")
values('Milan', 'Reljić', 'Svetosavska 10', '1503992184533','0612904856','5154oo98');

insert into "Zaposleni"("ImeZaposlenog","PrezimeZaposlenog","Adresa","JMBG","KontaktZaposlenog","Lozinka")
values('Dragana', 'Aćimoviić', 'Nemanjina 34', '1303997184532','0645878943','4288qw18');

--Kupac

insert into "Kupac"("ImeKupca","PrezimeKupca","Adresa","JMBG","Pol","KontaktKupca","Datum_rodjenja","Aktivan","GotovinskoStanje")
values('Milica', 'Pantić', 'Kneza Miloša 4', '1212000184532','zensko','0645898354','2001-12-12','true',555268888.25);

insert into "Kupac"("ImeKupca","PrezimeKupca","Adresa","JMBG","Pol","KontaktKupca","Datum_rodjenja","Aktivan","GotovinskoStanje")
values('Darko', 'Popović', 'Nikole Pašiča 29', '2001998184532','musko','0645508114','1998-01-20','true',5400000.59);

insert into "Kupac"("ImeKupca","PrezimeKupca","Adresa","JMBG","Pol","KontaktKupca","Datum_rodjenja","Aktivan","GotovinskoStanje")
values('Lazar', 'Vasković', 'Kralja Milana 41', '1211997888888','musko','0645115522','1997-11-12','false', 1000000000.00);

insert into "Kupac"("ImeKupca","PrezimeKupca","Adresa","JMBG","Pol","KontaktKupca","Datum_rodjenja","Aktivan","GotovinskoStanje")
values('Milan', 'Reljić', 'Svetosavska 15', '3003999184638','musko','0612907777','1999-03-30','false',70540258.59);

insert into "Kupac"("ImeKupca","PrezimeKupca","Adresa","JMBG","Pol","KontaktKupca","Datum_rodjenja","Aktivan","GotovinskoStanje")
values('Dragana', 'Aćimović', 'Banjalučka 44', '1210995184582','zensko','0645877512','1995-10-12','true', 8712300.65);

--Narudzbina

insert into "Narudzbina"("Datum","Primarnost","Kolicina","ZaposleniID","KupacID")
values('2021-03-05','A',78,1,2);

insert into "Narudzbina"("Datum","Primarnost","Kolicina","ZaposleniID","KupacID")
values('2021-11-05','B',104,2,3);

insert into "Narudzbina"("Datum","Primarnost","Kolicina","ZaposleniID","KupacID")
values('2021-10-11','A',15,1,1);

insert into "Narudzbina"("Datum","Primarnost","Kolicina","ZaposleniID","KupacID")
values('2021-08-25','A',89,3,4);

insert into "Narudzbina"("Datum","Primarnost","Kolicina","ZaposleniID","KupacID")
values('2021-11-07','C',101,2,1);


--Potvrda

insert into "Potvrda"("IzvrsenaTransakcija","NarudzbinaID")
values('true',1);

insert into "Potvrda"("IzvrsenaTransakcija","NarudzbinaID")
values('true',2);

insert into "Potvrda"("IzvrsenaTransakcija","NarudzbinaID")
values('true',3);

insert into "Potvrda"("IzvrsenaTransakcija","NarudzbinaID")
values('false',4);

insert into "Potvrda"("IzvrsenaTransakcija","NarudzbinaID")
values('false',2);

--Slika
insert into "Slika"("Naziv_slike","Cena","PeriodID","TipSlikeID","SlikarID","NarudzbinaID")
values('Mona Lisa',1700000.78,1,2,1,1);

insert into "Slika"("Naziv_slike","Cena","PeriodID","TipSlikeID","SlikarID","NarudzbinaID")
values('Seoba Srba',2500000.00,5,2,5,3);

insert into "Slika"("Naziv_slike","Cena","PeriodID","TipSlikeID","SlikarID","NarudzbinaID")
values('Okovani Prometej',1700000.00,2,2,3,5);

insert into "Slika"("Naziv_slike","Cena","PeriodID","TipSlikeID","SlikarID","NarudzbinaID")
values('Rođenje Venere',4500000.00,1,2,2,4);

insert into "Slika"("Naziv_slike","Cena","PeriodID","TipSlikeID","SlikarID","NarudzbinaID")
values('3. Maj 1808', 7650000.00,4,2,4,2);
