## Set GOOGLE_APPLICATION_CREDENTIALS=
There a few ways to "activate" application default credentials:

Use your user account
``` 
Run gcloud auth application-default login
```
Use service account (preferred), create service account assign required privilege 
and download key file.

set environment variable 
```
GOOGLE_APPLICATION_CREDENTIALS=path/to/your/service_accont_key_file.json
copy path/to/your/service_accont_key_file.json to ~/.config/gcloud/application_default_credentials.json`
```
now below command should work. 
```
gcloud auth application-default print-access-token
```


## Installing the emulator
The Datastore emulator is a component of the Google Cloud SDK's gcloud tool. Use the gcloud components install command to install the Datastore emulator:
```
gcloud components install cloud-datastore-emulator
```
Starting the emulator
Start the emulator by executing datastore start from a command prompt:
```
gcloud beta emulators datastore start [flags]
```
### Automatically setting the variables

Run env-init using command substitution:
```
$(gcloud beta emulators datastore env-init)
```