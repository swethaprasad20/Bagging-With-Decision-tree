import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author swethaprasad
 *
 *This project construct decision tree by scanning through the data set given as an input
 *Algorithm used is ID3
 *At each level , attribute with maximum info gain is selected.
 *Branching is stopped when entropy of a node is 0 or all the attributes have been evaluated in the path of the node.
 *Class value that occurs maximum in the given node is chosen. If both the class value are equal in number , then the class value
 *that occurs the most in given training set is chosen.
 *
 *Input: Training set and test set
 *output: Decision tree, Accuracy of the tree on training set and test set.
 *
 *This class deals with reading training set & test set , calling tree construction module and testing accuracy of the tree.
 */

public class MainModule {

	public static void main(String[] args){


		IOUtilities ioUtilities = new IOUtilities();
		try{


			// reads the input file
			ExampleSet trainingSet = ioUtilities.readAttributes(args[0],true);

			//reads the test file
			ExampleSet testSet=ioUtilities.readAttributes(args[1],false);

			//if there are no examples in the training set , return;
			if(trainingSet.getTrainingExamples()==null || trainingSet.getTrainingExamples().size()==0){
				return;
			}
			//setting number of attributes in the example
			trainingSet.setNoOfAttributesInExample(trainingSet.getTrainingExamples().get(0).getAttr().length);

			//Creating bags
			Bagging bagging= new Bagging();
			List<ExampleSet> trainingSetBags=bagging.createBags(trainingSet, 10);

			System.out.println("Algorithm take approximately 0.1 minute per bag to compute the output");
			//calling tree construction module
			List<String> decisionTreeExpression= new ArrayList<String>();

			//Constructing tree for each of the bags
			TreeConstruction treeConstruction= new TreeConstruction();
			for(ExampleSet trainingSetExample:trainingSetBags){

				decisionTreeExpression.add(treeConstruction.constructTree(trainingSetExample));
			}
		
			
			TestDecisionTree testDecisionTree = new TestDecisionTree();
			//Testing decision tree on test Set by voting
			System.out.println("\n \nAccuracy on test set ("+testSet.getTrainingExamples().size()+" instances ) : "+MathLibrary.roundToThreeDecimal(testDecisionTree.testDecisionTree(testSet,decisionTreeExpression))+"%");


			//Testing decision tree on training set by voting
			System.out.println("Accuracy on training set("+trainingSet.getTrainingExamples().size()+" instances) : "+MathLibrary.roundToThreeDecimal(testDecisionTree.testDecisionTree(trainingSet,decisionTreeExpression))+"%");;

		}catch(MalformedURLException e){
			System.out.println("Entered URL is not in the right format. \n URL should look like http://www.hlt.utdallas.edu/~yangl/cs6375/homework/hw1/");

		}catch(IOException e1){
			System.out.println("Exception occured while reding input files. Make sure you are connected to the internet.");
		}
	}
}
