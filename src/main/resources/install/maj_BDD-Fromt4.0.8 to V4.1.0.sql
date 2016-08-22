
--  !!!!!!! Attention !!!!!!!!!
-- par Miled Rousset 
-- 
-- La base de données change pour : 
-- modification de la gestion des Groupes et sous groupes / Collections et sous collections.
--
--  !!!!!!! Attention !!!!!!!!! 


-- Table: public.concept_group_concept

DROP TABLE public.concept_group_concept;

CREATE TABLE public.concept_group_concept
(
    idgroup text COLLATE "default".pg_catalog NOT NULL,
    idthesaurus text COLLATE "default".pg_catalog NOT NULL,
    idconcept text COLLATE "default".pg_catalog NOT NULL,
    CONSTRAINT concept_group_concept_pkey PRIMARY KEY (idthesaurus, idgroup, idconcept)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.concept_group_concept
    OWNER to opentheso;


-- Suppression de la colonne idconcept de la table concept_group
alter table public.concept_group drop column idconcept;

alter table public.concept_group drop column idparentgroup;



