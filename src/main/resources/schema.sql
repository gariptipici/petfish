;
CREATE USER IF NOT EXISTS "SA" SALT 'fd1dd0584d6dd082' HASH '8916550d5c86c6cb867968f077e049946acc00b01f41569b3ba108d6c0a2e57b' ADMIN;
CREATE SEQUENCE "PUBLIC"."HIBERNATE_SEQUENCE" START WITH 11;
CREATE MEMORY TABLE "PUBLIC"."AQUARIUM"(
                                           "ID" BIGINT NOT NULL,
                                           "GLASS_TYPE" VARCHAR(255),
                                           "SHAPE" VARCHAR(255),
                                           "SIZE" DOUBLE,
                                           "UNIT" INTEGER
);
ALTER TABLE "PUBLIC"."AQUARIUM" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_B" PRIMARY KEY("ID");
-- 3 +/- SELECT COUNT(*) FROM PUBLIC.AQUARIUM;

CREATE MEMORY TABLE "PUBLIC"."FISH"(
                                       "ID" BIGINT NOT NULL,
                                       "COLOR" VARCHAR(255),
                                       "NUMBER_OF_FINS" INTEGER,
                                       "SPECIES" INTEGER,
                                       "AQUARIUM_ID" BIGINT NOT NULL
);
ALTER TABLE "PUBLIC"."FISH" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_2" PRIMARY KEY("ID");
-- 7 +/- SELECT COUNT(*) FROM PUBLIC.FISH;

ALTER TABLE "PUBLIC"."FISH" ADD CONSTRAINT "PUBLIC"."FKR0JQGUKF12IPOJAQHVG41J0HH" FOREIGN KEY("AQUARIUM_ID") REFERENCES "PUBLIC"."AQUARIUM"("ID") NOCHECK;
