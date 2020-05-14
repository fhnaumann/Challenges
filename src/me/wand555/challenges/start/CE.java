package me.wand555.challenges.start;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import me.wand555.Challenge.DeathRun.DeathRunHandler;
import me.wand555.Challenge.DeathRun.Conversations.ConversationsHandler;
import me.wand555.challenges.settings.challengeprofile.Backpack;
import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.position.Position;
import me.wand555.challenges.settings.challengeprofile.position.PositionManager;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.challengeprofile.types.sharedhealth.SharedHealthChallenge;
import me.wand555.challenges.settings.config.LanguageMessages;
import me.wand555.challenges.settings.config.WorldUtil;
import me.wand555.challenges.settings.gui.GUI;
import me.wand555.challenges.settings.gui.GUIType;
import me.wand555.challenges.settings.gui.InventoryManager;
import me.wand555.challenges.settings.gui.SignMenuFactory;
import me.wand555.challenges.settings.timer.TimerOrder;

public class CE implements CommandExecutor {
	
	private Challenges plugin;
	private GUI gui;
	private SignMenuFactory signMenuFactory;
	
	public CE(Challenges plugin, GUI gui, SignMenuFactory signMenuFactory) {
		this.plugin = plugin;
		this.gui = gui;
		this.signMenuFactory = signMenuFactory;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Only for players");
			return false;
		}
		
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		Player player = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("challenge")) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("join")) {
					if(player.hasPermission("challenge.join")) {
						if(!ChallengeProfile.getInstance().isDone) {
							if(!ChallengeProfile.getInstance().isInChallenge(player)) {
								if(!ChallengeProfile.getInstance().isRestarted) {
									//load player in challenge information
									//store normal world information
									WorldUtil.storePlayerInformationBeforeChallenge(player);
									WorldUtil.loadPlayerInformationInChallengeAndApply(player);
									
									ChallengeProfile.getInstance().addToParticipants(player);
									ChallengeProfile.getInstance().addToScoreBoard(player);
									player.sendMessage(LanguageMessages.teleportMsg);
								}
								else {
									player.sendMessage(LanguageMessages.resetWarning);
								}
							}
							else {
								player.sendMessage(LanguageMessages.alreadyInChallenge);
							}
						}
						else {
							player.sendMessage(LanguageMessages.noChallengeToJoin);
						}
					}
				}
				else if(args[0].equalsIgnoreCase("leave")) {
					if(player.hasPermission("challenge.leave")) {
						if(ChallengeProfile.getInstance().isInChallenge(player)) {
							//store player in challenge information
							//load normal world information
							WorldUtil.storePlayerInformationInChallenge(player);
							WorldUtil.loadPlayerInformationBeforeChallengeAndApply(player);
							
							ChallengeProfile.getInstance().removeFromParticipants(player);
							ChallengeProfile.getInstance().removeFromScoreBoard(player);
							player.sendMessage(LanguageMessages.teleportMsg);
						}
						else {
							player.sendMessage(LanguageMessages.notInChallenge);
						}
					}
				}
				else if(args[0].equalsIgnoreCase("restore")) {
					if(player.hasPermission("challenge.restore")) {
						if(ChallengeProfile.getInstance().isDone) {
							ChallengeProfile.getInstance().restoreChallenge();
							
						}
						else {
							player.sendMessage(LanguageMessages.noChallengeToRestore);
						}
						
					}
				}
				else if(args[0].equalsIgnoreCase("reset")) {
					if(player.hasPermission("challenge.reset")) {
						if(!ChallengeProfile.getInstance().isRestarted) {
							if(plugin.getConfig().getBoolean("autoReset")) {
								ChallengeProfile.getInstance().resetChallenge();
								//send waiting message
								Bukkit.getScheduler().runTaskLater(plugin, () -> {
									ChallengeProfile.getInstance().sendMessageToAllParticipants(LanguageMessages.loadingWorlds);
									Challenges.initializeWorlds();	
									Bukkit.getScheduler().runTaskLater(plugin, () -> {
										ChallengeProfile.getInstance().getParticipants().forEach(p -> {
											p.sendMessage(LanguageMessages.teleportMsg);
											WorldUtil.storePlayerInformationBeforeChallenge(p);
											WorldUtil.loadPlayerInformationInChallengeAndApply(p);
										});
									}, 20L);
								}, 5*20L);
							}
							else {
								ChallengeProfile.getInstance().resetChallenge();
								player.sendMessage(LanguageMessages.deletedChallengeWorlds);
								player.sendMessage(LanguageMessages.resetWarning);
							}
							
						}	
						else {
							player.sendMessage(LanguageMessages.resetWarning);
						}
					}
				}
				else {
					player.sendMessage(LanguageMessages.challengeOptionSyntax);
				}
			}
			else {
				player.sendMessage(LanguageMessages.challengeOptionSyntax);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("timer")) {
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("start")) {
					if(player.hasPermission("timer.start")) {
						if(ChallengeProfile.getInstance().isInChallenge(player)) {
							if(!ChallengeProfile.getInstance().hasStarted) {
								if(args[1].equalsIgnoreCase("asc")) {
									ChallengeProfile.getInstance().closeOtherPlayerSettingsGUI();
									ChallengeProfile.getInstance().startTimer(TimerOrder.ASC);
									ChallengeProfile.getInstance().closeOtherPlayerSettingsGUI();
								}
								else if(args[1].equalsIgnoreCase("desc")) {
									ChallengeProfile.getInstance().closeOtherPlayerSettingsGUI();
									ChallengeProfile.getInstance().displayTimerDescendingEnterGUI(signMenuFactory, player);	
								}
								else {
									player.sendMessage(LanguageMessages.timerStartSyntax);
								}	
							}
							else {
								player.sendMessage(LanguageMessages.timerAlreadyStarted);
							}
						}
						else {
							player.sendMessage(LanguageMessages.notInChallenge);
						}
					}
				}
			}
			else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("pause")) {
					if(player.hasPermission("timer.pause")) {
						if(ChallengeProfile.getInstance().isInChallenge(player)) {
							if(ChallengeProfile.getInstance().canTakeEffect()) {
								ChallengeProfile.getInstance().pauseTimer();
							}
							else if(ChallengeProfile.getInstance().isPaused && !ChallengeProfile.getInstance().isDone) {
								ChallengeProfile.getInstance().resumeTimer();
								ChallengeProfile.getInstance().closeOtherPlayerSettingsGUI();
							}
							else {
								player.sendMessage(LanguageMessages.noPauseBecauseNotRunning);
							}
						}
						else {
							player.sendMessage(LanguageMessages.notInChallenge);
						}
					}
				}
				else if(args[0].equalsIgnoreCase("set")) {
					if(player.hasPermission("timer.set")) {
						if(ChallengeProfile.getInstance().isInChallenge(player)) {
							if(ChallengeProfile.getInstance().hasStarted && !ChallengeProfile.getInstance().isDone) {
								ChallengeProfile.getInstance().closeOtherPlayerSettingsGUI();
								ChallengeProfile.getInstance().displayTimerSetEnterGUI(signMenuFactory, player);
							}
							else {
								player.sendMessage(LanguageMessages.timerNotSettable);
							}
						}
						else {
							player.sendMessage(LanguageMessages.notInChallenge);
						}
					}
				}
				else if(args[0].equalsIgnoreCase("reset")) {
					if(player.hasPermission("timer.reset")) {
						if(ChallengeProfile.getInstance().isInChallenge(player)) {
							if(ChallengeProfile.getInstance().hasStarted && !ChallengeProfile.getInstance().isDone) {
								ChallengeProfile.getInstance().closeOtherPlayerSettingsGUI();
								if(ChallengeProfile.getInstance().getSecondTimer().getOrder() == TimerOrder.ASC) {
									ChallengeProfile.getInstance().getSecondTimer().setTime(0);
								}
								else {
									ChallengeProfile.getInstance().getSecondTimer().setTime(1);
								}
							}
							else {
								player.sendMessage(LanguageMessages.timerNotSettable);
							}
						}
						else {
							player.sendMessage(LanguageMessages.notInChallenge);
						}
					}
				}
				else {
					player.sendMessage(LanguageMessages.timerOptionSyntax);
					player.sendMessage(LanguageMessages.timerStartSyntax);
				}
			}
			else {
				player.sendMessage(LanguageMessages.timerOptionSyntax);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("pos")) {
			PositionManager posManager = ChallengeProfile.getInstance().getPosManager();
			if(args.length == 0) {
				if(player.hasPermission("challenge.pos.list")) {
					if(posManager.getPositions().isEmpty()) player.sendMessage("NO POS EXIST ADD TRANSLATION");
					posManager.getPositions().forEach(pos -> {
						player.sendMessage(posManager.displayPosition(pos));
					});
				}
			}
			else if(args.length == 1) {
				if(player.hasPermission("challenge.pos.add")) {
					if(posManager.positionWithNameExists(args[0])) {
						player.sendMessage(posManager.displayPosition(posManager.getPositionFromName(args[0])));
					}
					else {
						Position pos = new Position(args[0], player.getLocation(), player.getUniqueId(), new Date());
						posManager.addToPositions(pos);
						player.sendMessage(LanguageMessages.registeredPosition.replace("[POS]", args[0]));
						ChallengeProfile.getInstance().getParticipants()
							.forEach(p -> p.sendMessage(posManager.displayPosition(pos)));
					}
				}
			}
			else {
				player.sendMessage(LanguageMessages.positionSyntax);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("bp")) {
			if(args.length == 0) {
				if(player.hasPermission("challenge.bp")) {
					if(ChallengeProfile.getInstance().isInChallenge(player)) {
						if(ChallengeProfile.getInstance().getBackpack().isEnabled()) {
							player.openInventory(InventoryManager.getInventoryManager().getBackpackGUI());
						}
						else {
							player.sendMessage(LanguageMessages.backpackDisabled);
						}
						
					}
					else {
						player.sendMessage(LanguageMessages.notInChallenge);
					}
				}
			}
			else {
				player.sendMessage(LanguageMessages.bpSyntax);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("hp")) {
			if(args.length == 2) {
				if(player.hasPermission("challenge.hp")) {
					if(ChallengeProfile.getInstance().isInChallenge(player)) {
						if(args[1].equalsIgnoreCase("all")) {
							ChallengeProfile.getInstance().getParticipants().forEach(p -> {
								if(StringUtils.isNumeric(args[0])) {
									double number = Double.valueOf(args[0]);
									p.setHealth(number < 0 ? 
											0 : 
										number > p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() ? 
												p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() 
												: number);
									p.setFoodLevel(22);
									player.sendMessage(LanguageMessages.setHP);
								}
								else {
									player.sendMessage(LanguageMessages.notANumber.replace("[NUMBER]", args[0]));
								}
							});
							if(GenericChallenge.isActive(ChallengeType.SHARED_HEALTH)) {
								((SharedHealthChallenge)GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH)).setSharedHealth(player.getHealth());
							}
						}
						else {
							@SuppressWarnings("deprecation")
							OfflinePlayer offlinetarget = Bukkit.getOfflinePlayer(args[1]);
							if(offlinetarget.isOnline()) {
								Player target = (Player) offlinetarget;
								if(ChallengeProfile.getInstance().isInChallenge(player)) {
									if(StringUtils.isNumeric(args[0])) {
										double number = Double.valueOf(args[0]);
										if(GenericChallenge.isActive(ChallengeType.SHARED_HEALTH)) {
											ChallengeProfile.getInstance().getParticipants().forEach(p -> {
												p.setHealth(number < 0 ? 
														0 : 
													number > p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() ? 
															p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() 
															: number);
												p.setFoodLevel(22);
												player.sendMessage(LanguageMessages.setHP);
												if(GenericChallenge.isActive(ChallengeType.SHARED_HEALTH)) {
													((SharedHealthChallenge)GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH)).setSharedHealth(p.getHealth());
												}
											});
										}
										else {
											target.setHealth(number < 0 ? 
													0 : 
												number > target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() ? 
														target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() 
														: number);
											target.setFoodLevel(22);
										}
										
										player.sendMessage(LanguageMessages.setHP);
									}
									else {
										player.sendMessage(LanguageMessages.notANumber.replace("[NUMBER]", args[0]));
									}
								}
								else {
									//target not in challenge
								}
							}
							else {
								player.sendMessage(LanguageMessages.playerNotOnline.replace("[PLAYER]", offlinetarget.getName()));
							}
						}	
					}
					else {
						player.sendMessage(LanguageMessages.notInChallenge);
					}
				}
			}
			else {
				player.sendMessage(LanguageMessages.hpOptionSyntax);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("settings")) {
			if(args.length == 0) {
				if(player.hasPermission("challenge.settings.view")) {
					if(cProfile.isInChallenge(player)) {
						//if(cProfile.isPaused || !cProfile.hasStarted) {
							//create settings gui
							gui.createGUI(player, GUIType.OVERVIEW, -1, null);
						//}
						//else {
						//	player.sendMessage(LanguageMessages.noSettingsHasToBePaused);
						//}
					}
					else {
						player.sendMessage(LanguageMessages.notInChallenge);
					}
				}
			}
			else {
				player.sendMessage(LanguageMessages.settingSyntax);
			}
		}
		
		//------------------------------------DEATHRUN-----------------------------
		else if(cmd.getName().equalsIgnoreCase("deathrun")) {
			if(args.length == 0) {
				if(player.hasPermission("challenge.deathrun")) {
					if(cProfile.isInChallenge(player)) {
						ConversationsHandler.getConversationsHandler().startConversation(player);
					}
					else {
						
					}
				}
				else {
					
				}
			}
			else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("start")) {
					if(player.hasPermission("challenge.deathrun.start")) {
						if(cProfile.isInChallenge(player)) {
							if(cProfile.isDeathRunMode()) {
								//DeathRunHandler.getDeathRunHandler().startDeathRunCountdown(plugin);
							}
							else {
								
							}
						}
						else {
							
						}
					}
				}
			}
			else {
				
			}
		}
		return true;
	}
}
