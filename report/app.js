var fetchData = function(){
                    $.ajax({
                        url: "http://jenni.gift/get_report.php",
                        success: function(data) {
                            var temp = JSON.parse(data);

//                            blinkArray = temp.blinkArray;
//                            slouchArray = temp.slouchArray;

                            console.log(data);

                            
                            }
                    });
                   // setTimeout(fetchData, 5000);	
                };

                
