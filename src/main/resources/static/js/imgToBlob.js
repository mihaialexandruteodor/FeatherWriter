function encodeImageFileAsURL(element) {
  var file = element.files[0];
  var reader = new FileReader();
  reader.onloadend = function() {
    //console.log('RESULT', reader.result)
    document.getElementById('blobVal').value = reader.result;
  }
  reader.readAsDataURL(file);
}