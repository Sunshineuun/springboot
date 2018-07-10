package com.qiushengming.entity;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Table;

/**
 * @author qiushengming
 * @date 2018/7/6
 */
@Table(value = "KBMS__DISEASE_INFO", resultMapId = "DiseaseInfoMap")
public class DiseaseInfo extends BaseEntity {
    /** ICD10-卫计委版 */
    private String icd10; 

    /** 疾病名称 */
    private String name; 

    /** 疾病类别 */
    private String type; 

    /** 来源说明 */
    private String sourceDsc; 

    /** 英文名 */
    private String enName; 

    /** 别名 */
    private String alias; 

    /** 概述 */
    private String outline; 

    /** 流行病学 */
    private String epidemiology; 

    /** 疾病分期/分类 */
    private String diseaseStage; 

    /** 病因 */
    private String etiology; 

    /** 病理/发病机制 */
    private String pathogenesis; 

    /** 临床表现 */
    private String clinicalFeature; 

    /** 相关检查 */
    private String relevantExamination; 

    /** 诊断标准 */
    private String diagnosticCriteria; 

    /** 鉴别诊断 */
    private String differentialDiagnosis; 

    /** 并发症 */
    private String complication; 

    /** 治疗方案 */
    private String therapeuticSchedule; 

    /** 预后 */
    private String prognosis; 

    /** 预防 */
    private String precaution; 

    /** 别名-来源于DT组提供 */
    private String aliasDt; 

   @Column(value = "ICD10", chineseName = "ICD10-卫计委版")
   public String getIcd10() {
        return icd10;
    }

   public void setIcd10(String icd10) {
        this.icd10=icd10;
    }

   @Column(value = "NAME", chineseName = "疾病名称")
   public String getName() {
        return name;
    }

   public void setName(String name) {
        this.name=name;
    }

   @Column(value = "TYPE", chineseName = "疾病类别")
   public String getType() {
        return type;
    }

   public void setType(String type) {
        this.type=type;
    }

   @Column(value = "SOURCE_DSC", chineseName = "来源说明")
   public String getSourceDsc() {
        return sourceDsc;
    }

   public void setSourceDsc(String sourceDsc) {
        this.sourceDsc=sourceDsc;
    }

   @Column(value = "EN_NAME", chineseName = "英文名")
   public String getEnName() {
        return enName;
    }

   public void setEnName(String enName) {
        this.enName=enName;
    }

   @Column(value = "ALIAS", chineseName = "别名")
   public String getAlias() {
        return alias;
    }

   public void setAlias(String alias) {
        this.alias=alias;
    }

   @Column(value = "OUTLINE", chineseName = "概述")
   public String getOutline() {
        return outline;
    }

   public void setOutline(String outline) {
        this.outline=outline;
    }

   @Column(value = "EPIDEMIOLOGY", chineseName = "流行病学")
   public String getEpidemiology() {
        return epidemiology;
    }

   public void setEpidemiology(String epidemiology) {
        this.epidemiology=epidemiology;
    }

   @Column(value = "DISEASE_STAGE", chineseName = "疾病分期/分类")
   public String getDiseaseStage() {
        return diseaseStage;
    }

   public void setDiseaseStage(String diseaseStage) {
        this.diseaseStage=diseaseStage;
    }

   @Column(value = "ETIOLOGY", chineseName = "病因")
   public String getEtiology() {
        return etiology;
    }

   public void setEtiology(String etiology) {
        this.etiology=etiology;
    }

   @Column(value = "PATHOGENESIS", chineseName = "病理/发病机制")
   public String getPathogenesis() {
        return pathogenesis;
    }

   public void setPathogenesis(String pathogenesis) {
        this.pathogenesis=pathogenesis;
    }

   @Column(value = "CLINICAL_FEATURE", chineseName = "临床表现")
   public String getClinicalFeature() {
        return clinicalFeature;
    }

   public void setClinicalFeature(String clinicalFeature) {
        this.clinicalFeature=clinicalFeature;
    }

   @Column(value = "RELEVANT_EXAMINATION", chineseName = "相关检查")
   public String getRelevantExamination() {
        return relevantExamination;
    }

   public void setRelevantExamination(String relevantExamination) {
        this.relevantExamination=relevantExamination;
    }

   @Column(value = "DIAGNOSTIC_CRITERIA", chineseName = "诊断标准")
   public String getDiagnosticCriteria() {
        return diagnosticCriteria;
    }

   public void setDiagnosticCriteria(String diagnosticCriteria) {
        this.diagnosticCriteria=diagnosticCriteria;
    }

   @Column(value = "DIFFERENTIAL_DIAGNOSIS", chineseName = "鉴别诊断")
   public String getDifferentialDiagnosis() {
        return differentialDiagnosis;
    }

   public void setDifferentialDiagnosis(String differentialDiagnosis) {
        this.differentialDiagnosis=differentialDiagnosis;
    }

   @Column(value = "COMPLICATION", chineseName = "并发症")
   public String getComplication() {
        return complication;
    }

   public void setComplication(String complication) {
        this.complication=complication;
    }

   @Column(value = "THERAPEUTIC_SCHEDULE", chineseName = "治疗方案")
   public String getTherapeuticSchedule() {
        return therapeuticSchedule;
    }

   public void setTherapeuticSchedule(String therapeuticSchedule) {
        this.therapeuticSchedule=therapeuticSchedule;
    }

   @Column(value = "PROGNOSIS", chineseName = "预后")
   public String getPrognosis() {
        return prognosis;
    }

   public void setPrognosis(String prognosis) {
        this.prognosis=prognosis;
    }

   @Column(value = "PRECAUTION", chineseName = "预防")
   public String getPrecaution() {
        return precaution;
    }

   public void setPrecaution(String precaution) {
        this.precaution=precaution;
    }

   @Column(value = "ALIAS_DT", chineseName = "别名-来源于DT组提供")
   public String getAliasDt() {
        return aliasDt;
    }

   public void setAliasDt(String aliasDt) {
        this.aliasDt=aliasDt;
    }

}
