<?php

include_once('config.php');



		$handle = new mysqli(DBHOST, DBUSER, DBPASS, DBNAME);
		if ($handle->connect_error) {
		    die("Connection failed: " . $conn->connect_error);
		}
		$generateBlinkReportQuery = "SELECT blinks.count from blinks;";

		// $getLastBlinkReportQuery = "SELECT `created` FROM blinks ORDER BY `created` DESC LIMIT 1";
		$lastBlinkRecordResult = $handle->query($generateBlinkReportQuery);
		$results = array();

		if ($lastBlinkRecordResult) {
			$numRows = mysqli_num_rows($lastBlinkRecordResult);

			if ($numRows > 0) {
				while ($row = $lastBlinkRecordResult->fetch_assoc()) {

					$results[] = (int) $row['count'];
				}
			}
		}
		// var_dump($results);
		$js_array = json_encode($results);
		// echo "var javascript_array = ". $js_array . ";\n";
		header('Content-Type: application/json');
		echo $js_array;



?>
