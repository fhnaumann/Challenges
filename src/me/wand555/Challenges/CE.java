package me.wand555.Challenges;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import me.wand555.Challenges.ChallengeProfile.Backpack;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.SharedHealthChallenge.SharedHealthChallenge;
import me.wand555.Challenges.ChallengeProfile.Positions.Position;
import me.wand555.Challenges.ChallengeProfile.Positions.PositionManager;
import me.wand555.Challenges.Config.LanguageMessages;
import me.wand555.Challenges.Config.WorldUtil;
import me.wand555.Challenges.Timer.TimerOrder;
import me.wand555.GUI.GUI;
import me.wand555.GUI.GUIType;
import me.wand555.GUI.SignMenuFactory;

public class CE implements CommandExecutor {
	
	private GUI gui;
	private SignMenuFactory signMenuFactory;
	
	public CE(GUI gui, SignMenuFactory signMenuFactory) {
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
							if(!ChallengeProfile.getInstance().isInChallenge(player.getUniqueId())) {
								if(!ChallengeProfile.getInstance().isRestarted) {
									//load player in challenge information
									//store normal world information
									WorldUtil.storePlayerInformationBeforeChallenge(player);
									WorldUtil.loadPlayerInformationInChallengeAndApply(player);
									
									ChallengeProfile.getInstance().addToParticipants(player.getUniqueId());
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
						if(ChallengeProfile.getInstance().isInChallenge(player.getUniqueId())) {
							//store player in challenge information
							//load normal world information
							WorldUtil.storePlayerInformationInChallenge(player);
							WorldUtil.loadPlayerInformationBeforeChallengeAndApply(player);
							
							ChallengeProfile.getInstance().removeFromParticipants(player.getUniqueId());
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
							if(ChallengeProfile.getInstance().getSecondTimer().getOrder() == TimerOrder.ASC) {
								ChallengeProfile.getInstance().restoreChallenge();
							}
							else {
								player.sendMessage(LanguageMessages.noRestoreBecauseDesc);
							}
							
						}
						else {
							player.sendMessage(LanguageMessages.noChallengeToRestore);
						}
						
					}
				}
				else if(args[0].equalsIgnoreCase("reset")) {
					if(player.hasPermission("challenge.reset")) {
						if(!ChallengeProfile.getInstance().isRestarted) {
							ChallengeProfile.getInstance().resetChallenge();
							player.sendMessage(LanguageMessages.deletedChallengeWorlds);
							player.sendMessage(LanguageMessages.resetWarning);
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
						if(ChallengeProfile.getInstance().isInChallenge(player.getUniqueId())) {
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
						if(ChallengeProfile.getInstance().isInChallenge(player.getUniqueId())) {
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
						ChallengeProfile.getInstance().getParticipantsAsPlayers()
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
					if(ChallengeProfile.getInstance().isInChallenge(player.getUniqueId())) {
						if(ChallengeProfile.getInstance().getBackpack().isEnabled()) {
							gui.createGUI(player, GUIType.BACKPACK);
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
					if(ChallengeProfile.getInstance().isInChallenge(player.getUniqueId())) {
						if(args[1].equalsIgnoreCase("all")) {
							ChallengeProfile.getInstance().getParticipantsAsPlayers().forEach(p -> {
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
								if(ChallengeProfile.getInstance().isInChallenge(player.getUniqueId())) {
									if(StringUtils.isNumeric(args[0])) {
										double number = Double.valueOf(args[0]);
										if(GenericChallenge.isActive(ChallengeType.SHARED_HEALTH)) {
											ChallengeProfile.getInstance().getParticipantsAsPlayers().forEach(p -> {
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
					if(cProfile.isInChallenge(player.getUniqueId())) {
						if(cProfile.isPaused || !cProfile.hasStarted) {
							//create settings gui
							gui.createGUI(player, GUIType.OVERVIEW);
						}
						else {
							player.sendMessage(LanguageMessages.noSettingsHasToBePaused);
						}
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
		return true;
	}
}
