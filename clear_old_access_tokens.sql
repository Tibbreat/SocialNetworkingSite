DELIMITER //

CREATE PROCEDURE clear_old_access_tokens()
BEGIN
    UPDATE User 
    SET accessToken = NULL 
    WHERE accessToken IS NOT NULL
    AND TIMESTAMPDIFF(MINUTE, dateOfBirth, NOW()) >= 30;
END //

DELIMITER ;
CREATE EVENT IF NOT EXISTS clear_old_tokens_event
ON SCHEDULE EVERY 1 MINUTE
DO
CALL clear_old_access_tokens();
