<?php

include_once('config.php');

	if (!empty($_GET)) {
		if (!empty($_GET['type']) && !empty($_GET['time'])) {
			logEvent();
		}
	} else {
		// do somethin better here
		return false;
	}

	function logEvent() {
		$eventType = array('blink', 'movement', 'slouch');
		$blink = "blink";
		$movement = "movement";
		$slouch = "slouch";

		$type = htmlentities($_GET['type']);
		$time = htmlentities($_GET['time']);

		// Do this is the time received is a unix timestamp and not ISO 8601
		$time = gmdate("Y-m-d\TH:i:s\Z", $time);

		if (!in_array($type, $eventType)) {
			// also do something better
			echo 'not an option';
			return false;
		}

		$db_handle = new mysqli(DBHOST, DBUSER, DBPASS, DBNAME);
		if ($db_handle->connect_error) {
		    die("Connection failed: " . $conn->connect_error);
		}

		$logEventQuery = "INSERT INTO events (type, time) VALUES ('$type', '$time');";
		$result = $db_handle->query($logEventQuery);

		if ($result) {
		    $lastId = $db_handle->insert_id;
		    echo "New record created successfully. Last inserted ID is: " . $lastId;
		} else {
		    echo "Error: " . $logEventQuery . "<br>" . $db_handle->connect_error;
		}
	}

?>
