/******************************************************************************************
*
* Script for Flyway setup for data migration
*
*******************************************************************************************/

create schema crns;
use crns;

CREATE TABLE scriptlog (
  version_rank INT(11) NOT NULL,
  installed_rank INT(11) NOT NULL,
  version NVARCHAR(50) NOT NULL,
  description NVARCHAR(200),
  type NVARCHAR(20) NOT NULL,
  script NVARCHAR(1000) NOT NULL,
  checksum INT(11),
  installed_by NVARCHAR(100) NOT NULL,
  installed_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  execution_time INT(11) NOT NULL,
  success BINARY NOT NULL,
  CONSTRAINT PK_ScriptLog PRIMARY KEY (version)
);

CREATE INDEX IX_ScriptLog_version_rank ON scriptlog (version_rank);
CREATE INDEX IX_ScriptLog_installed_rank ON scriptlog (installed_rank);
CREATE INDEX IX_ScriptLog_success ON scriptlog (success);

-- Add the baseline entry
insert into scriptlog (version_rank, installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success)
values (1, 1, '001', '<< Flyway Baseline >>', 'BASELINE', '<< Flyway Baseline >>', null, CURRENT_USER, CURRENT_TIMESTAMP, 0, 1)