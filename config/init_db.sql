CREATE TABLE resume
(
  uuid      CHAR(36) NOT NULL
    CONSTRAINT resume_pkey
    PRIMARY KEY,
  full_name TEXT     NOT NULL
);

CREATE TABLE contact
(
  id          SERIAL NOT NULL
    CONSTRAINT contact_id_pk
    PRIMARY KEY,
  resume_uuid CHAR(36)
    CONSTRAINT contact_resume_uuid_fk
    REFERENCES resume
    ON DELETE CASCADE,
  type        TEXT   NOT NULL,
  value       TEXT   NOT NULL

);
CREATE UNIQUE INDEX contact_resume_uuid_type_uindex
  ON contact (resume_uuid, type);