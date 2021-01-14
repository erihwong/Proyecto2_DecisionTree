
#se usar seleccion el direcctorio del script como directorio de
#archivos
setwd(dirname(rstudioapi::getSourceEditorContext()$path))

#se lee el CSV y se lo asigna a una tabla
tabla<- read.csv('pacientes.csv')
print(tabla)

library(pROC)
threshold2 <- function(predict, response) {
  r <- pROC::roc(response, predict)
  r$thresholds[which.max(r$sensitivities + r$specificities)]
}

f<-pROC::roc(tabla$DEATH_EVENT, tabla$serum_sodium)
plot.roc(f)

#Umbral de edad
threshold2(tabla$age, tabla$DEATH_EVENT)
#Umbral de CPK_enzyme
threshold2(tabla$CPK_enzyme, tabla$DEATH_EVENT)
#Umbral de ejection_fraction
threshold2(tabla$ejection_fraction, tabla$DEATH_EVENT)
#Umbral de platelets
threshold2(tabla$platelets, tabla$DEATH_EVENT)
#Umbral de serum_creatinine
threshold2(tabla$serum_creatinine, tabla$DEATH_EVENT)
#Umbral de serum_sodium
threshold2(tabla$serum_sodium, tabla$DEATH_EVENT)
