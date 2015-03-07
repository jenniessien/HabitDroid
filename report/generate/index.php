#!/usr/bin/env php
<?php

include_once('config.php');

logReports();

	function logReports() {
		echo "This script will run every minute.";

		// find all blinks/movements/slouches within the hour just past. When cron runs at 14:00:00 Get count for 13:00:00 - 13:59:59 and delete them
		$db_handle = new mysqli(DBHOST, DBUSER, DBPASS, DBNAME);
		if ($db_handle->connect_error) {
		    die("Connection failed: " . $conn->connect_error);
		}

		// Get the most recent count from the tables
		// Get the hour it was done within
		$getLastBlinkReportQuery = "SELECT `created` FROM blinks ORDER BY `created` DESC LIMIT 1";
		$lastBlinkRecordResult = $db_handle->query($getLastBlinkReportQuery);

		if ($lastBlinkRecordResult) {
			$numRows = mysqli_num_rows($lastBlinkRecordResult);

			if ($numRows > 0) {
				$result = $lastBlinkRecordResult->fetch_assoc();
			} else {
				$result = 0;
			}
		}

		echo $result;

		// $findTotalBlinksQuery = "SELECT * FROM events WHERE ";

		// $findTotalMovementsQuery = "";

		// $findTotalSlouchesQuery = "";

		// $php_array = array(1, 2, 30);
		// $js_array = json_encode($php_array);
		// echo "var javascript_array = ". $js_array . ";\n";
	}
?>
