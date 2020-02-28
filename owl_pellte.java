#__author__ = 'liujing'
package 解析;


import org.apache.jena.rdf.model.*;

import java.util.Iterator;

import org.apache.jena.ontology.*;  
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.EntitySearcher.getAnnotationObjects;
import static org.semanticweb.owlapi.search.Searcher.sup;
//import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;
//import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
//import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;
//import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.vocab.OWLFacet.MAX_EXCLUSIVE;
import static org.semanticweb.owlapi.vocab.OWLFacet.MIN_INCLUSIVE;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.PrintUtil;
import org.junit.Ignore;
import org.junit.Test;
//import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
//import org.semanticweb.owlapi.model.OWLSupeterjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.search.Filters;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredClassAssertionAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDataPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDisjointClassesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentDataPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredInverseObjectPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredObjectPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredPropertyAssertionGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubDataPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.OWLEntityRemover;
import org.semanticweb.owlapi.util.OWLOntologyMerger;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitorEx;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
//import org.semanticweb.reasonerfactory.pellet.PelletReasonerFactory;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

import edu.stanford.smi.protegex.owl.model.OWLDatatypeProperty;
import uk.ac.manchester.cs.owlapi.modularity.ModuleType;
import uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor;

public class Example{
	public static void main(String[] args) throws Exception { 
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		File file = new File("D:\\高血压2.0\\知识图谱\\Screening_Hypertension_ontology_100.owl");
		OWLOntology ont = man.loadOntologyFromOntologyDocument(file);
		String base = "http://www.semanticweb.org/daleye/ontologies/2018/6/Hypertention-ontology.owl#";
		OWLDataFactory dataFactory = man.getOWLDataFactory();
		OWLClass personClass = dataFactory.getOWLClass(IRI.create(base + "#Person"));
		OWLIndividual patientA= dataFactory.getOWLNamedIndividual(IRI.create(base + "#patientA"));
		OWLObjectProperty 有患者基本信息 = dataFactory.getOWLObjectProperty(IRI.create(base + "#有患者基本信息"));
		OWLIndividual patientA患者基本信息 = dataFactory.getOWLNamedIndividual(IRI.create(base + "#patientA患者基本信息"));
		OWLObjectPropertyAssertionAxiom assertion1 = dataFactory.getOWLObjectPropertyAssertionAxiom(有患者基本信息, patientA,
		patientA患者基本信息);
		AddAxiom addAxiomChange1 = new AddAxiom(ont, assertion1);
		man.applyChange(addAxiomChange1);
		OWLDataProperty Age = dataFactory.getOWLDataProperty(IRI.create(base + "#Age"));
		OWLDataProperty name = dataFactory.getOWLDataProperty(IRI.create(base + "#name"));
		OWLDataProperty dob = dataFactory.getOWLDataProperty(IRI.create(base + "#dob"));
		OWLDataProperty gender = dataFactory.getOWLDataProperty(IRI.create(base + "#gender"));
		OWLDataProperty 信息录入时间 = dataFactory.getOWLDataProperty(IRI.create(base + "#信息录入时间"));
		OWLLiteral litname = dataFactory.getOWLLiteral("Alice");
		OWLLiteral litgender = dataFactory.getOWLLiteral("female");
		OWLLiteral litdob = dataFactory.getOWLLiteral("1993-7-28");
		OWLLiteral litdate = dataFactory.getOWLLiteral("2018-11-1");
		OWLAxiom axiom11 = dataFactory.getOWLDataPropertyAssertionAxiom(name, patientA患者基本信息,litname);
		OWLAxiom axiom12 = dataFactory.getOWLDataPropertyAssertionAxiom(Age, patientA患者基本信息, 25);
		OWLAxiom axiom13 = dataFactory.getOWLDataPropertyAssertionAxiom(dob, patientA患者基本信息, litdob);
		OWLAxiom axiom14 = dataFactory.getOWLDataPropertyAssertionAxiom(gender, patientA患者基本信息, litgender);
		OWLAxiom axiom15 = dataFactory.getOWLDataPropertyAssertionAxiom(信息录入时间, patientA患者基本信息, litdate);
		man.applyChange(new AddAxiom(ont,(axiom11)));
		man.applyChange(new AddAxiom(ont,(axiom12)));
		man.applyChange(new AddAxiom(ont,(axiom13)));
		man.applyChange(new AddAxiom(ont,(axiom14)));
		man.applyChange(new AddAxiom(ont,(axiom15)));
		OWLClassAssertionAxiom ax = dataFactory.getOWLClassAssertionAxiom(personClass, patientA);
		man.addAxiom(ont, ax);
		man.saveOntology(ont, IRI.create("file:/高血压2.0/知识图谱/temp.owl"));
//		String prefix = ont.getOntologyID().getOntologyIRI().get() + "#";

		OWLReasonerFactory reasonerFactory= new PelletReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ont);
		System.out.println(reasoner.getReasonerName()+"版本信息："+reasoner.getReasonerVersion());
		
		List<InferredAxiomGenerator<? extends OWLAxiom>> gens = new ArrayList<>();
        gens.add(new InferredSubClassAxiomGenerator());  
        gens.add(new InferredClassAssertionAxiomGenerator());
        gens.add(new InferredDataPropertyCharacteristicAxiomGenerator());
        gens.add( new InferredDisjointClassesAxiomGenerator());
        gens.add( new InferredEquivalentClassAxiomGenerator());
        gens.add( new InferredEquivalentDataPropertiesAxiomGenerator());
        gens.add( new InferredEquivalentObjectPropertyAxiomGenerator());
        gens.add( new InferredInverseObjectPropertiesAxiomGenerator());
        gens.add( new InferredObjectPropertyCharacteristicAxiomGenerator());
        gens.add( new InferredPropertyAssertionGenerator());
        gens.add( new InferredSubDataPropertyAxiomGenerator());
        gens.add( new InferredSubObjectPropertyAxiomGenerator());
        

        InferredOntologyGenerator iog = new InferredOntologyGenerator(reasoner, gens);
        OWLOntology infOnt = man.createOntology();
        iog.fillOntology(dataFactory, infOnt);
        man.saveOntology(infOnt,new RDFXMLDocumentFormat(),IRI.create(new File("D://Inference result1.owl")));
        
        Model ontoModel = ModelFactory.createDefaultModel();        
        ontoModel.read(new FileInputStream("D://Inference result1.owl"), "");
        
/*  以下是输出与病人1有关的所有信息
 *        String p1 = "http://www.semanticweb.org/daleye/ontologies/2018/6/Hypertention-ontology.owl#病人1";
 *                printStatements(ontoModel, ontoModel.getResource(p1), null, null);
	}
	
        public static void printStatements(Model m, Resource s, Property p,Resource o) {
            for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
                Statement stmt = i.nextStatement();
                System.out.println(" - " + PrintUtil.print(stmt));
 */
        String queryString1 = "SELECT ?x WHERE {<http://www.semanticweb.org/daleye/ontologies/2018/6/Hypertention-ontology.owl#病人2> <http://www.semanticweb.org/daleye/ontologies/2018/6/Hypertention-ontology.owl#患/有疾病> ?x. }";
        String queryString2 = "SELECT ?x WHERE {<http://www.semanticweb.org/daleye/ontologies/2018/6/Hypertention-ontology.owl#病人2> <http://www.semanticweb.org/daleye/ontologies/2018/6/Hypertention-ontology.owl#需要追加检查> ?x. }";
        String queryString3 = "SELECT ?x WHERE {<http://www.semanticweb.org/daleye/ontologies/2018/6/Hypertention-ontology.owl#病人2> <http://www.semanticweb.org/daleye/ontologies/2018/6/Hypertention-ontology.owl#需要追加确诊检查> ?x. }";
        String queryString4 = "SELECT ?x WHERE {<http://www.semanticweb.org/daleye/ontologies/2018/6/Hypertention-ontology.owl#病人2> <http://www.semanticweb.org/daleye/ontologies/2018/6/Hypertention-ontology.owl#需要追加分型检查> ?x. }";
        String queryString5 = "SELECT ?x WHERE {<http://www.semanticweb.org/daleye/ontologies/2018/6/Hypertention-ontology.owl#病人2> <http://www.semanticweb.org/daleye/ontologies/2018/6/Hypertention-ontology.owl#给予药物治疗> ?x. }";
        String queryString6 = "SELECT ?x WHERE {<http://www.semanticweb.org/daleye/ontologies/2018/6/Hypertention-ontology.owl#病人2> <http://www.semanticweb.org/daleye/ontologies/2018/6/Hypertention-ontology.owl#给予专科治疗> ?x. }";
        ResultSet rs1 = queryResult("D://Inference result1.owl",queryString1);
        ResultSet rs2 = queryResult("D://Inference result1.owl",queryString2);
        ResultSet rs3 = queryResult("D://Inference result1.owl",queryString3);
        ResultSet rs4 = queryResult("D://Inference result1.owl",queryString4);
        ResultSet rs5 = queryResult("D://Inference result1.owl",queryString5);
        ResultSet rs6 = queryResult("D://Inference result1.owl",queryString6);
        ResultSetFormatter.out(System.out, rs1);
        ResultSetFormatter.out(System.out, rs2);
        ResultSetFormatter.out(System.out, rs3);
        ResultSetFormatter.out(System.out, rs4);
        ResultSetFormatter.out(System.out, rs5);
        ResultSetFormatter.out(System.out, rs6);    
	}
	public static ResultSet queryResult(String path, String queryString) {
	    // 创建模型
	    Model model = ModelFactory.createDefaultModel();
	    model.read(path);
	    // 创建查询
	    Query query = QueryFactory.create(queryString);
	    // 创建查询执行对象
	    QueryExecution queryExecution = QueryExecutionFactory.create(query,model);
	    // 执行查询，生成结果
	    ResultSet rs = queryExecution.execSelect();
	    return rs;
	   }
            }
        



     


