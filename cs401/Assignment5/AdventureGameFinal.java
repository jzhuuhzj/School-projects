/*
Jingxia Zhu
CS0401
2017 Spring
Assignment 5
*/

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import java.util.Random;


public class AdventureGameFinal extends Application {
  private BorderPane scenePane;
  private Text titleText;
  private HBox topBox;
  private HBox bottomBox;
  private Button runSimButton;
  private Button exitButton;
  private Stage stage;

  // player section components
  private Text playerSectionTitle;
  private ListView <String> playerListView;
  private ImageView playerImageView;
  private ListView <String> playerWeaponListView;
  private ImageView playerWeaponImageView;
  private VBox playerPane;
  private Button selectPlayerButton;

  //enemy section components
  private Text enemySectionTitle;
  private ListView <String> enemyListView;
  private ImageView enemyImageView;
  private ListView <String> enemyWeaponListView;
  private ImageView enemyWeaponImageView; 
  private VBox enemyPane;
  private Button selectEnemyButton;

  //Center Section components
  private Text centerSectionTitle;
  private Label numOfEnemyTitle;
  private RadioButton rdoNumEnm1;
  private RadioButton rdoNumEnm2;
  private RadioButton rdoNumEnm3;
  
  private Label numOfRoundsTitle;
  private RadioButton rdoNumRnd1;
  private RadioButton rdoNumRnd2;
  private RadioButton rdoNumRnd3;
  private RadioButton rdoNumRnd4;
  private VBox centerPane_upper;
  private VBox centerPane_lower;
  private VBox centerPane;


  private boolean playerIsSelected;
  private boolean enemyIsSelected;


  //add in photos
  private static final String PALADIN_IMG = "file:/Users/zhujx/Desktop/art/Paladin.png";
  private static final String ROGUE_IMG = "file:/Users/zhujx/Desktop/art/Rogue.png";
  private static final String JACKIE_CHAN_IMG = "file:/Users/zhujx/Desktop/art/JackieChan.png";
  private static final String GOBLIN_IMG = "file:/Users/zhujx/Desktop/art/Goblin.png";
  private static final String SKELETON_IMG = "file:/Users/zhujx/Desktop/art/Skeleton.png";
  private static final String MACE_IMG = "file:/Users/zhujx/Desktop/art/Mace.png";
  private static final String SHORT_SWORD_IMG = "file:/Users/zhujx/Desktop/art/ShortSword.png";
  private static final String LONG_SWORD_IMG = "file:/Users/zhujx/Desktop/art/LongSword.png";
  private static final String AXE_IMG = "file:/Users/zhujx/Desktop/art/Axe.png";

  //attributes of weapons
  private static final int SHORT_SWORD_MIN = 1;
  private static final int SHORT_SWORD_MAX = 4;
  private static final int LONG_SWORD_MIN = 3;
  private static final int LONG_SWORD_MAX = 7;
  private static final int AXE_MIN = 2;
  private static final int AXE_MAX = 6;
  private static final int MACE_MIN = 2;
  private static final int MACE_MAX = 6;

 
  @Override  //annotation - syntactic metadata
  public void start(Stage primaryStage) {  
    stage = primaryStage;
    playerIsSelected = false;
    enemyIsSelected = false;

    //build the playerSection
    playerSectionTitle = new Text("Player");
    playerSectionTitle.setFont(new Font(15));
    playerListView = new ListView <String> ();
    playerListView.getItems().addAll("Rogue","Paladin","Jackie Chan");

    if (playerImageView == null)
      playerImageView = new ImageView();

    playerWeaponListView = new ListView <String> ();
    playerWeaponListView.getItems().addAll("Mace","Short Sword","Long Sword","Axe");

    enemyWeaponListView = new ListView <String> ();
    enemyWeaponListView.getItems().addAll("Mace","Short Sword","Long Sword","Axe");
    
    if (playerWeaponImageView == null)
      playerWeaponImageView = new ImageView();

    if (enemyWeaponImageView == null)
      enemyWeaponImageView = new ImageView();


    selectPlayerButton = new Button("SELECT PLAYER");
       
  
    selectPlayerButton.setOnAction(e -> setPlayerImage());

   

    selectEnemyButton = new Button("SELECT ENEMY");
    
    selectEnemyButton.setOnAction(e -> setEnemyImage());

   
   
   // if((playerSectionTitle != null))
    playerPane = new VBox(20, playerSectionTitle, playerListView, playerImageView, playerWeaponListView, playerWeaponImageView, selectPlayerButton);
    playerPane.setPadding(new Insets(20,20,20,20));
    
    //build the Enemy section
    enemySectionTitle = new Text("Enemy");
    enemySectionTitle.setFont(new Font(15));
    enemyListView = new ListView <String>();
    enemyListView.getItems().addAll("Goblin","Skeleton");

    if (enemyImageView == null)
      enemyImageView = new ImageView();


    enemyPane = new VBox(20, enemySectionTitle, enemyListView, enemyImageView, enemyWeaponListView, enemyWeaponImageView, selectEnemyButton);
    enemyPane.setPadding(new Insets(20,20,20,20));

    //build the center section
    centerSectionTitle = new Text("Options");
    centerSectionTitle.setFont(new Font(15));
    numOfEnemyTitle = new Label ("Number of enemies");
    rdoNumEnm1 = new RadioButton("4 enemies");
    rdoNumEnm2 = new RadioButton("5 enemies");
    rdoNumEnm3 = new RadioButton("6 enemies");
    ToggleGroup groupNumEnm = new ToggleGroup();
    rdoNumEnm1.setToggleGroup(groupNumEnm);
    rdoNumEnm2.setToggleGroup(groupNumEnm);
    rdoNumEnm3.setToggleGroup(groupNumEnm);

    rdoNumEnm1.setSelected(true);
    
    numOfRoundsTitle = new Label("Number of rounds");
    rdoNumRnd1 = new RadioButton("   1 round");
    rdoNumRnd2 = new RadioButton(" 5 rounds");
    rdoNumRnd3 = new RadioButton("10 rounds");
    rdoNumRnd4 = new RadioButton("20 rounds");
    ToggleGroup groupNumRnd = new ToggleGroup();
    rdoNumRnd1.setToggleGroup(groupNumRnd);
    rdoNumRnd2.setToggleGroup(groupNumRnd);
    rdoNumRnd3.setToggleGroup(groupNumRnd);
    rdoNumRnd4.setToggleGroup(groupNumRnd);

    rdoNumRnd1.setSelected(true);


    centerPane_upper = new VBox(20, centerSectionTitle, numOfEnemyTitle,rdoNumEnm1,rdoNumEnm2, rdoNumEnm3);
    
    centerPane_lower = new VBox(20, numOfRoundsTitle,rdoNumRnd1,rdoNumRnd2,rdoNumRnd3, rdoNumRnd4);
    
    centerPane_upper.setAlignment(Pos.CENTER);
    centerPane_lower.setAlignment(Pos.CENTER);

    centerPane = new VBox(50, centerPane_upper, centerPane_lower);
    centerPane.setAlignment(Pos.CENTER);


    //build the titleText
    titleText = new Text("ADVENTURE GAME BATTLE SIMULATOR");
    titleText.setFont(new Font(25));

    //build the topBox
    topBox = new HBox(titleText);
    topBox.setAlignment(Pos.CENTER);

    //build the bottom pane
    runSimButton = new Button("RUN SIMULATION");

    runSimButton.setDisable(true);

    runSimButton.setOnAction(e -> simulation());

    
    exitButton = new Button("EXIT");
    exitButton.setOnAction(e -> exit());

    bottomBox = new HBox(100, runSimButton, exitButton);
    bottomBox.setAlignment(Pos.CENTER);
    bottomBox.setPadding(new Insets(20,20,20,20));

    //build the scene pane
    scenePane = new BorderPane();
    scenePane.setTop(topBox);
    scenePane.setBottom(bottomBox);
    scenePane.setLeft(playerPane);
    scenePane.setRight(enemyPane);
    scenePane.setCenter(centerPane);
   

    //build the scene
    Scene scene = new Scene (scenePane, 1000, 800);
    primaryStage.setScene(scene);
    primaryStage.show();     

  }
  
  public void exit(){
    stage.close();
  }

  public void setPlayerImage(){  
    Image playerImage = null;
    Image playerWeaponImage = null;
    String selectedPlayer = playerListView.getSelectionModel().getSelectedItem();
    String selectedPlayerWeapon = playerWeaponListView.getSelectionModel().getSelectedItem();
    Image enemyImage = null;
    Image enemyWeaponImage = null;
    String selectedEnemy = enemyListView.getSelectionModel().getSelectedItem();
    String selectedEnemyWeapon = enemyWeaponListView.getSelectionModel().getSelectedItem();

    if ((selectedPlayerWeapon == null)&&(selectedPlayer == null))
      MessageBox.show("You must select a character and a weapon.", "ERROR");
    else if (selectedPlayer == null)
      MessageBox.show("You must select a character.", "ERROR");
    else if (selectedPlayerWeapon == null)
      MessageBox.show("You must select a weapon.", "ERROR");    
    else if (selectedPlayer.equals("Paladin")){
      playerImage = new Image(PALADIN_IMG);
        if (selectedPlayerWeapon.equals("Short Sword"))
          playerWeaponImage = new Image(SHORT_SWORD_IMG);
        else if (selectedPlayerWeapon.equals("Long Sword"))
          playerWeaponImage = new Image(LONG_SWORD_IMG);
        else if (selectedPlayerWeapon.equals("Mace"))
          playerWeaponImage = new Image(MACE_IMG);
        else if (selectedPlayerWeapon.equals("Axe"))
          playerWeaponImage = new Image(AXE_IMG);
        playerWeaponImageView.setImage(playerWeaponImage); 
      playerImageView.setImage(playerImage);
    }
    else if (selectedPlayer.equals("Rogue")){
      playerImage = new Image(ROGUE_IMG);
        if (selectedPlayerWeapon.equals("Short Sword"))
          playerWeaponImage = new Image(SHORT_SWORD_IMG);
        else if (selectedPlayerWeapon.equals("Long Sword"))
          playerWeaponImage = new Image(LONG_SWORD_IMG);
        else if (selectedPlayerWeapon.equals("Mace"))
          playerWeaponImage = new Image(MACE_IMG);
        else if (selectedPlayerWeapon.equals("Axe"))
          playerWeaponImage = new Image(AXE_IMG);
        playerWeaponImageView.setImage(playerWeaponImage); 
      playerImageView.setImage(playerImage);
    }
    else if (selectedPlayer.equals("Jackie Chan")){
      playerImage = new Image(JACKIE_CHAN_IMG);
        if (selectedPlayerWeapon.equals("Short Sword"))
          playerWeaponImage = new Image(SHORT_SWORD_IMG);
        else if (selectedPlayerWeapon.equals("Long Sword"))
          playerWeaponImage = new Image(LONG_SWORD_IMG);
        else if (selectedPlayerWeapon.equals("Mace"))
          playerWeaponImage = new Image(MACE_IMG);
        else if (selectedPlayerWeapon.equals("Axe"))
          playerWeaponImage = new Image(AXE_IMG);
        playerWeaponImageView.setImage(playerWeaponImage); 
      playerImageView.setImage(playerImage);
    }
    playerIsSelected = true;
    if((selectedEnemy != null) && (selectedPlayer != null) && (selectedEnemyWeapon != null) && (selectedPlayerWeapon!= null) && (playerIsSelected == true) && (enemyIsSelected == true))
      runSimButton.setDisable(false);
    
  
  }

  public void setEnemyImage(){

    Image playerImage = null;
    Image playerWeaponImage = null;
    String selectedPlayer = playerListView.getSelectionModel().getSelectedItem();
    String selectedPlayerWeapon = playerWeaponListView.getSelectionModel().getSelectedItem();
    Image enemyImage = null;
    Image enemyWeaponImage = null;
    String selectedEnemy = enemyListView.getSelectionModel().getSelectedItem();
    String selectedEnemyWeapon = enemyWeaponListView.getSelectionModel().getSelectedItem();
    
    if ((selectedEnemyWeapon == null)&&(selectedEnemy == null))
      MessageBox.show("You must select a character and a weapon.", "ERROR");
    else if (selectedEnemy == null)
      MessageBox.show("You must select a character.", "ERROR");
    else if (selectedEnemyWeapon == null)
      MessageBox.show("You must select a weapon.", "ERROR"); 
    else if (selectedEnemy.equals("Goblin")){
      enemyImage = new Image(GOBLIN_IMG);
        if (selectedEnemyWeapon.equals("Short Sword"))
          enemyWeaponImage = new Image(SHORT_SWORD_IMG);
        else if (selectedEnemyWeapon.equals("Long Sword"))
          enemyWeaponImage = new Image(LONG_SWORD_IMG);
        else if (selectedEnemyWeapon.equals("Mace"))
          enemyWeaponImage = new Image(MACE_IMG);
        else if (selectedEnemyWeapon.equals("Axe"))
          enemyWeaponImage = new Image(AXE_IMG);  
        enemyWeaponImageView.setImage(enemyWeaponImage);
      enemyImageView.setImage(enemyImage); 
    }
    else if (selectedEnemy.equals("Skeleton")){
      enemyImage = new Image(SKELETON_IMG);
        if (selectedEnemyWeapon.equals("Short Sword"))
          enemyWeaponImage = new Image(SHORT_SWORD_IMG);
        else if (selectedEnemyWeapon.equals("Long Sword"))
          enemyWeaponImage = new Image(LONG_SWORD_IMG);
        else if (selectedEnemyWeapon.equals("Mace"))
          enemyWeaponImage = new Image(MACE_IMG);
        else if (selectedEnemyWeapon.equals("Axe"))
          enemyWeaponImage = new Image(AXE_IMG);  
        enemyWeaponImageView.setImage(enemyWeaponImage);
      enemyImageView.setImage(enemyImage); 
    }

    enemyIsSelected = true;
    if((selectedPlayer != null) && (selectedPlayerWeapon != null) && (selectedEnemy != null) && (selectedEnemyWeapon != null) && (playerIsSelected == true) && (enemyIsSelected == true))
      runSimButton.setDisable(false);
    
    
  }

  public void simulation(){

    Random randomNums = new Random();

    String selectedPlayer = playerListView.getSelectionModel().getSelectedItem();
    String selectedPlayerWeapon = playerWeaponListView.getSelectionModel().getSelectedItem();

    String selectedEnemy = enemyListView.getSelectionModel().getSelectedItem();
    String selectedEnemyWeapon = enemyWeaponListView.getSelectionModel().getSelectedItem();

    
    String msg = "";

    boolean isNumEnmSelected1 = rdoNumEnm1.isSelected();
    boolean isNumEnmSelected2 = rdoNumEnm2.isSelected();
    boolean isNumEnmSelected3 = rdoNumEnm3.isSelected();
    
    boolean isNumRndSelected1 = rdoNumRnd1.isSelected();
    boolean isNumRndSelected2 = rdoNumRnd2.isSelected();
    boolean isNumRndSelected3 = rdoNumRnd3.isSelected();
    boolean isNumRndSelected4 = rdoNumRnd4.isSelected();

    int numEnemyPerRnd = 0;
    int numEnemyTotal = 0;
    int numRound = 0;
    int battlesWon = 0;
    int battlesLost = 0;
    int numEnemyDefeated = 0;

    final int ROGUE_INIT_HP = 55;
    final int ROGUE_INIT_STRENGTH = 8;
    final int PALADIN_INIT_HP = 35;
    final int PALADIN_INIT_STRENGTH = 14;
    final int CHAN_INIT_HP = 45;
    final int CHAN_INIT_STRENGTH = 10;

    final int MINION_INIT_HP = 25;
    final int GOBLIN_INIT_STRENGTH = 4;
    final int SKELETON_INIT_STRENGTH = 3;

    int[] player = new int[4]; // stores the player attributes
    int[] enemy = new int[4];  // stores the enemy attributes


    int playerDamage, playerATK;
    int enemyDamage, enemyATK;
    int playerActionChoice, randomNumAnswer;

    if (selectedPlayerWeapon.equals("Short Sword")){
      player[2] = SHORT_SWORD_MIN;
      player[3] = SHORT_SWORD_MAX;
    }
    else if (selectedPlayerWeapon.equals("Long Sword")){
      player[2] = LONG_SWORD_MIN;
      player[3] = LONG_SWORD_MAX;
    }
    else if (selectedPlayerWeapon.equals("Mace")){
      player[2] = MACE_MIN;
      player[3] = MACE_MAX;   
    }
    else if (selectedPlayerWeapon.equals("Axe")){
      player[2] = AXE_MIN;
      player[3] = AXE_MAX;
    }

   

    if (selectedEnemyWeapon.equals("Short Sword")){
      player[2] = SHORT_SWORD_MIN;
      player[3] = SHORT_SWORD_MAX;
    }
    else if (selectedEnemyWeapon.equals("Long Sword")){
      player[2] = LONG_SWORD_MIN;
      player[3] = LONG_SWORD_MAX;    
    }
    else if (selectedEnemyWeapon.equals("Mace")){
      player[2] = MACE_MIN;
      player[3] = MACE_MAX;  
    }
    else if (selectedEnemyWeapon.equals("Axe")){
      player[2] = AXE_MIN;
      player[3] = AXE_MAX; 
    }
    

    

    if (selectedPlayer == "Rogue"){
      player[0] = ROGUE_INIT_HP;
      player[1] = ROGUE_INIT_STRENGTH;
      
    }
    else if (selectedPlayer == "Paladin"){
      player[0] = PALADIN_INIT_HP;
      player[1] = PALADIN_INIT_STRENGTH;
      
    }    
    else if (selectedPlayer == "Jackie Chan"){
      player[0] = CHAN_INIT_HP;
      player[1] = CHAN_INIT_STRENGTH;
    }

    if (selectedEnemy == "Goblin"){
      enemy[0] = MINION_INIT_HP;
      enemy[1] = GOBLIN_INIT_STRENGTH;     
    }
    
    else if (selectedEnemy == "Skeleton"){
      enemy[0] = MINION_INIT_HP;
      enemy[1] = SKELETON_INIT_STRENGTH;  
    }
    
    if (rdoNumEnm1.isSelected()){
      numEnemyPerRnd = 4;
        if (rdoNumRnd1.isSelected())
          numRound = 1;
        else if (rdoNumRnd2.isSelected())
          numRound = 5;
        else if (rdoNumRnd3.isSelected())
          numRound = 10;
        else if (rdoNumRnd4.isSelected())
          numRound = 20;
    }
    else if (rdoNumEnm2.isSelected()){
      numEnemyPerRnd = 5;
        if (rdoNumRnd1.isSelected())
          numRound = 1;
        else if (rdoNumRnd2.isSelected())
          numRound = 5;
        else if (rdoNumRnd3.isSelected())
          numRound = 10;
        else if (rdoNumRnd4.isSelected())
          numRound = 20;
    }
    else if (rdoNumEnm3.isSelected()){
      numEnemyPerRnd= 6;
        if (rdoNumRnd1.isSelected())
          numRound = 1;
        else if (rdoNumRnd2.isSelected())
          numRound = 5;
        else if (rdoNumRnd3.isSelected())
          numRound = 10;
        else if (rdoNumRnd4.isSelected())
          numRound = 20;
    }

    numEnemyTotal = numEnemyPerRnd * numRound;


    for (int i = 1; i <= numRound; i++)
    {
      
      player[0] = 25;

      for (int j = 1; j <= numEnemyPerRnd; j++){
        enemy[0] = 25;
        while(enemy[0] > 0 && player[0] > 0)
        {
        playerDamage = randomNums.nextInt(player[3] - player[2] + 1) + player[2];
        playerATK = player[1] + playerDamage;
        enemy[0] -= playerATK;

        if (enemy[0] <= 0){ 
          numEnemyDefeated = numEnemyDefeated + 1;
          break;
        }

        enemyDamage = randomNums.nextInt(enemy[3] - enemy[2] + 1) + enemy[2];
        enemyATK = enemy[1] + enemyDamage;
        player[0] -= enemyATK;
   
        } // end of while loop
      }     
      if (player[0] > 0)
        battlesWon = battlesWon + 1; 

    } // end of for loop

  
    battlesLost = numRound - battlesWon;


    msg = msg + "Number of enemies per round: " + numEnemyPerRnd + "\n" + "Number of rounds: " + numRound + "\n" +"Number of battles won: " + battlesWon + "\n" +"Number of battles lost: " + battlesLost + "\n" + "Total number of enemies defeated: " + numEnemyDefeated;
    MessageBox.show(msg, "SIMULATION RESULTS");

  }

 
  public static void main(String[] args) {
    Application.launch(args);
  }
}
    
