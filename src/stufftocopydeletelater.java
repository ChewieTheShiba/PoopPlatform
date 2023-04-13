import java.awt.event.KeyEvent;

if(h != null && !h.equals(c1.getHitbox()) && !h.getId().equals((c1.getName())))
				{
					p1Intersection = h.intersects(c1.getHitbox());
					p1OppIntersection = h.getOppositeIntersection();
				}
				
				if(h != null && !h.equals(c2.getHitbox()) && !h.getId().equals(c2.getName()))
				{
					p2Intersection = h.intersects(c2.getHitbox());
					p2OppIntersection = h.getOppositeIntersection();
				}
				
				
				if(h != null && p1Intersection[0] != -1 && h.getDamage() != 0)
				{
					
				}
				
				
//ADD REPAINTS IN WHEN STUFF CHANGES
				
				

				if(characterSelect[p1SelectY][p1SelectX].getClass().equals(PoopDefender.class))
					c1 = new PoopDefender();
				if(characterSelect[p1SelectY][p1SelectX].getClass().equals(Neff.class))
					c1 = new Neff();
				if(characterSelect[p1SelectY][p1SelectX].getClass().equals(PoopDefender.class))
					c1 = new PoopDefender();
				if(characterSelect[p1SelectY][p1SelectX].getClass().equals(PoopDefender.class))
					c1 = new PoopDefender();
				c1.setName("Player 1");
				System.out.println(c1.getName());
				c1.setMoveLeft(false);
				c1.setMoveRight(false);
				c1.setJumping(false);
				c1.setDoubleJumping(false);
				c1.setH(500);
				c1.setK(-500);
				hitboxes.add(c1.attack1Hitbox);
				hitboxes.add(c1.attack2Hitbox);
				hitboxes.add(c1.attack3Hitbox);
				hitboxes.add(c1.attack4Hitbox);
				if(characterSelect[p2SelectY][p2SelectX].getClass().equals(PoopDefender.class))
					c2 = new PoopDefender();
				if(characterSelect[p2SelectY][p2SelectX].getClass().equals(Neff.class))
					c2 = new Neff();
				if(characterSelect[p2SelectY][p2SelectX].getClass().equals(PoopDefender.class))
					c2 = new PoopDefender();
				if(characterSelect[p2SelectY][p2SelectX].getClass().equals(PoopDefender.class))
					c2 = new PoopDefender();
				c2.setName("Player 2");
				c2.setMoveLeft(false);
				c2.setMoveRight(false);
				c2.setJumping(false);
				c2.setDoubleJumping(false);
				c2.setH(300);
				c2.setK(-1100);
				hitboxes.add(c2.attack1Hitbox);
				hitboxes.add(c2.attack2Hitbox);
				hitboxes.add(c2.attack3Hitbox);
				hitboxes.add(c2.attack4Hitbox);
				hitboxes.add(c1.getHitbox());
				hitboxes.add(c2.getHitbox());
				System.out.println(c1.getHitbox());
				System.out.println(c2.getHitbox());
			
			p1CharacterSelected = false;
			p2CharacterSelected = false;
			started = true;
			characterSelectReady = false;
			repaint();
			
			


			else if(p1Intersection[0] != -1 && h != null)
			{
				if(h.getMoveRight())
					updatePlayer1Position(px+(int)h.getKB(), py);
				if(h.getMoveLeft())
					updatePlayer1Position(px-(int)h.getKB(), py);
				if(h.getMoveUp())
					updatePlayer1Position(px, py-(int)h.getKB());
				if(h.getMoveDown())
					updatePlayer1Position(px, py+(int)h.getKB());
			}
				
				
				
				
				
				
				if(c1.getTryTilt() && !p1KnockingBack)
				{
					if(c1.getMoveRight())
					{
						c1.setMoveRight(false);
						c1.setTryTilt(false);
						c1.rightTilt();
						c1Image = c1.getCurrentPlayerImage();
					}
					if(c1.getMoveLeft())
					{
						c1.setMoveLeft(false);
						c1.setTryTilt(false);
						c1.leftTilt();
						c1Image = c1.getCurrentPlayerImage();
					}
					if(c1.getMoveUp()) 
					{
						c1.setMoveLeft(false);
						c1.setTryTilt(false);
						c1.upTilt();
						c1Image = c1.getCurrentPlayerImage();
					}
				}
				if(c1.getTrySpecial())
				{
					c1.special();
					hitboxes.add(c1.getSpecialProjectiles().get(c1.getSpecialProjectiles().size()-1));
					c1.setTrySpecial(false);
					c1Image = c1.getCurrentPlayerImage();
				}
				
				
				c1Image.paintIcon(this, g, px-c1.getXOffPut(), py-c1.getYOffPut());
				c2Image.paintIcon(this, g, sx-c2.getXOffPut(), sy-c2.getYOffPut());
				
				if((h.getH() < -150 || h.getH() > 2000 || h.getK() > 1200 || h.getK() < -100) && !h.equals(c1.getHitbox()) && !h.equals(c2.getHitbox()))
					hitboxes.remove(h);
				
				if(h != null && h.getId().equals("Projectile"))
					projectile.paintIcon(this, g, h.getH(), h.getK());
				
				
				case KeyEvent.VK_W:
					c1.setMoveUp(true);
					if(!c1.getJumping() && !c1.getTryTilt())
					{
						p1JumpStart = py;
						p1YVelocity = JUMPHEIGHT;
						c1.setJumping(true);
					}
					else if(c1.getJumping() && !c1.getDoubleJumping() && !c1.getTryTilt())
					{
						p1YVelocity = JUMPHEIGHT;
						c1.setDoubleJumping(true);
					}
					break;
				case KeyEvent.VK_X:
					c1.setTryTilt(true);
					break;
				case KeyEvent.VK_Z:
					c1.setTrySpecial(true);
					break;
					
					
				case KeyEvent.VK_A:
					c1.setMoveLeft(false);
					break;
				case KeyEvent.VK_D:
					c1.setMoveRight(false);
					break;
				case KeyEvent.VK_W:
					c1.setMoveUp(false);
					break;
				case KeyEvent.VK_SEMICOLON:
					c2.setMoveRight(false);
					break;
				case KeyEvent.VK_K:
					c2.setMoveLeft(false);
					break;
				case KeyEvent.VK_X:
					c1.setTryTilt(false);
					break;
				case KeyEvent.VK_Z:
					c1.setTrySpecial(false);
					break;
					
					for(int x = 0; x < hitboxes.size(); x++)
					{
						Hitbox h = hitboxes.get(x);
						if(h != null && (h.getH() < -150 || h.getH() > 2000 || h.getK() > 1200 || h.getK() < -100) && !h.equals(c1.getHitbox()) && !h.equals(c2.getHitbox()))
							hitboxes.remove(h);
					}
				
				
				
