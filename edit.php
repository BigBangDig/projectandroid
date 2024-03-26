<?php
	include "koneksi.php";
	
	$id = isset($_POST['id']) ? $_POST['id'] : '';
	class emp{}
	
	if (empty($id)) { 
		echo "Error Mengambil Data id kosong"; 
		
	} else {
		$query 	= mysqli_query($con,"SELECT * FROM pengguna WHERE id='".$id."'");
		$row 	= mysqli_fetch_array($query);
		
		if (!empty($row)) {
			$response = new emp();
			$response->id = $row["id"];
			$response->nik = $row["nik"];
			$response->nama = $row["nama"];
			$response->alamat = $row["alamat"];
			
			echo(json_encode($response));
		} else{ 
			
			echo("Error Mengambil Data"); 
		}	
	}
?>