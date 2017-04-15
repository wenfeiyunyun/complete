
    	window.addEventListener("DOMContentLoaded", function() {
			// Grab elements, create settings, etc.
            var canvas = document.getElementById('canvas');
            var context = canvas.getContext('2d');
            var video = document.getElementById('video');
            var mediaConfig =  { video: true };
            var errBack = function(e) {
            	console.log('An error has occurred!', e)
            };

			// Put video listeners into place
            if(navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
                navigator.mediaDevices.getUserMedia(mediaConfig).then(function(stream) {
                    video.src = window.URL.createObjectURL(stream);
                    video.play();
                });
            }

            /* Legacy code below! */
            else if(navigator.getUserMedia) { // Standard
				navigator.getUserMedia(mediaConfig, function(stream) {
					video.src = stream;
					video.play();
				}, errBack);
			} else if(navigator.webkitGetUserMedia) { // WebKit-prefixed
				navigator.webkitGetUserMedia(mediaConfig, function(stream){
					video.src = window.webkitURL.createObjectURL(stream);
					video.play();
				}, errBack);
			} else if(navigator.mozGetUserMedia) { // Mozilla-prefixed
				navigator.mozGetUserMedia(mediaConfig, function(stream){
					video.src = window.URL.createObjectURL(stream);
					video.play();
				}, errBack);
			}

			// Trigger photo take
			document.getElementById('snap').addEventListener('click', function() {
                /*///////////////////////////////////////////////////////////////
                //var video = new video;
                var maxW=150;
                var maxH=100;
                var iw=video.width;
                var ih=video.height;
                var scale=Math.max((maxW/iw),(maxH/ih));
                var iwScaled=iw*scale;
                var ihScaled=ih*scale;
                canvas.width=iwScaled;
                canvas.height=ihScaled;
                //context.drawImage(video,0,0,iwScaled,ihScaled);

                //////////////////////////////////////////////////////////////*/

				context.drawImage(video, 0, 0, 540, 405);
                var canvasServer = document.getElementById("canvas");
                //var context = canvasServer.getContext("2d");
                var imageData = canvasServer.toDataURL('image/jpeg');
                //var ajaxSaveCanvasURL = "/ajaxSaveCanvasURL?imageData="+"testinghere";
                alert("testing now!!!!??? ");
                var ajaxSaveCanvasURL = "/ajaxSaveCanvasURL";
                $.ajax({
                  method:   "POST",
                  url:      ajaxSaveCanvasURL,
                  data:     { 'imageData' : imageData }
                })
			});
		}, false);