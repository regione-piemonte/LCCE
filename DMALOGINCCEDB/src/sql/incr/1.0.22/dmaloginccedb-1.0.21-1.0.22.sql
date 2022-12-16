

CREATE OR REPLACE FUNCTION getGoodPwd( psw text)
 RETURNS text
 LANGUAGE plpgsql
AS $function$
begin
 RETURN REPLACE(psw, 'XX', '?');
END;
$function$
;





DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.22','Release 1.0.22', 'Incrementale', 'dmaloginccedb-1.0.21-1.0.22.sql','CSI', 0, true);
	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,20,'20',NOW(),'dmaloginccedb-1.0.21-1.0.22.sql');
		END IF;
	END IF;
END
$$;
