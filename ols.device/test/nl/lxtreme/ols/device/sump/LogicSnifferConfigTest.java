/*
 * OpenBench LogicSniffer / SUMP project 
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02110, USA
 *
 * 
 * Copyright (C) 2010-2011 J.W. Janssen, www.lxtreme.nl
 */
package nl.lxtreme.ols.device.sump;


import static org.junit.Assert.*;
import nl.lxtreme.ols.device.sump.profile.*;

import org.junit.*;


/**
 * Test cases for {@link SumpConfig}.
 */
public class LogicSnifferConfigTest
{
  // VARIABLES

  private SumpConfig config;
  private DeviceProfile profile;

  // METHODS

  /**
   * Sets up the test case.
   */
  @Before
  public void setUp() throws Exception
  {
    this.config = new SumpConfig();

    VirtualLogicSnifferDevice device = new VirtualLogicSnifferDevice( this.config );

    this.profile = device.addDeviceProfile( "MOCK", "MockedDevice" );
    this.config.setDeviceProfile( this.profile );

    device.close();
  }

  /**
   * Test method for {@link SumpConfig#getChannelCount()}.
   */
  @Test
  public void testGetChannelCount()
  {
    this.config.setSampleRate( 1 );
    assertEquals( 32, this.config.getChannelCount() );

    this.config.setSampleRate( LogicSnifferAcquisitionTask.CLOCK );
    assertEquals( 32, this.config.getChannelCount() );

    this.config.setSampleRate( LogicSnifferAcquisitionTask.CLOCK + 1 );
    assertEquals( 16, this.config.getChannelCount() );
  }

  /**
   * Test method for {@link SumpConfig#getClockspeed()}.
   */
  @Test
  public void testGetClockspeed()
  {
    assertEquals( LogicSnifferAcquisitionTask.CLOCK, this.config.getClockspeed() );
  }

  /**
   * Test method for {@link SumpConfig#getDeviceProfile()}.
   */
  @Test
  public void testGetDeviceProfile()
  {
    assertEquals( this.profile, this.config.getDeviceProfile() );
  }

  /**
   * Test method for {@link SumpConfig#getDivider()}.
   */
  @Test
  public void testGetDivider()
  {
    this.config.setSampleRate( Integer.MAX_VALUE );
    assertEquals( 0, this.config.getDivider() );

    this.config.setSampleRate( LogicSnifferAcquisitionTask.CLOCK );
    assertEquals( 0, this.config.getDivider() );

    this.config.setSampleRate( LogicSnifferAcquisitionTask.CLOCK >> 1 );
    assertEquals( 1, this.config.getDivider() );

    this.config.setSampleRate( LogicSnifferAcquisitionTask.CLOCK >> 2 );
    assertEquals( 3, this.config.getDivider() );
  }

  /**
   * Test method for {@link SumpConfig#getEnabledGroupCount()}.
   */
  @Test
  public void testGetEnabledGroupCountWithDDR()
  {
    // With DDR...
    this.config.setSampleRate( LogicSnifferAcquisitionTask.CLOCK + 1 );

    this.config.setEnabledChannels( 0 );
    assertEquals( 0, this.config.getEnabledGroupCount() );

    this.config.setEnabledChannels( 0x000000FF );
    assertEquals( 1, this.config.getEnabledGroupCount() );

    this.config.setEnabledChannels( 0x0000FFFF );
    assertEquals( 2, this.config.getEnabledGroupCount() );

    this.config.setEnabledChannels( 0x00FFFFFF );
    assertEquals( 2, this.config.getEnabledGroupCount() );

    this.config.setEnabledChannels( 0xFFFFFFFF );
    assertEquals( 2, this.config.getEnabledGroupCount() );
  }

  /**
   * Test method for {@link SumpConfig#getEnabledGroupCount()}.
   */
  @Test
  public void testGetEnabledGroupCountWithoutDDR()
  {
    // Without DDR...
    this.config.setSampleRate( 1 );

    this.config.setEnabledChannels( 0 );
    assertEquals( 0, this.config.getEnabledGroupCount() );

    this.config.setEnabledChannels( 0x000000FF );
    assertEquals( 1, this.config.getEnabledGroupCount() );

    this.config.setEnabledChannels( 0x0000FFFF );
    assertEquals( 2, this.config.getEnabledGroupCount() );

    this.config.setEnabledChannels( 0x00FFFFFF );
    assertEquals( 3, this.config.getEnabledGroupCount() );

    this.config.setEnabledChannels( 0xFFFFFFFF );
    assertEquals( 4, this.config.getEnabledGroupCount() );
  }

  /**
   * Test method for {@link SumpConfig#getGroupCount()}.
   */
  @Test
  public void testGetGroupCountWithDDR()
  {
    // With DDR...
    this.config.setSampleRate( LogicSnifferAcquisitionTask.CLOCK + 1 );

    assertEquals( 2, this.config.getGroupCount() );
  }

  /**
   * Test method for {@link SumpConfig#getGroupCount()}.
   */
  @Test
  public void testGetGroupCountWithoutDDR()
  {
    // Without DDR...
    this.config.setSampleRate( 1 );

    assertEquals( 4, this.config.getGroupCount() );
  }

  /**
   * Test method for {@link SumpConfig#getRLEDataWidth()}.
   */
  @Test
  public void testGetRLEDataWidthWithDDR()
  {
    // With DDR...
    this.config.setSampleRate( LogicSnifferAcquisitionTask.CLOCK + 1 );

    this.config.setEnabledChannels( 0 );
    assertEquals( 0, this.config.getRLEDataWidth() );

    this.config.setEnabledChannels( 0x000000FF );
    assertEquals( 8, this.config.getRLEDataWidth() );

    this.config.setEnabledChannels( 0x0000FFFF );
    assertEquals( 16, this.config.getRLEDataWidth() );

    this.config.setEnabledChannels( 0x00FFFFFF );
    assertEquals( 16, this.config.getRLEDataWidth() );

    this.config.setEnabledChannels( 0xFFFFFFFF );
    assertEquals( 16, this.config.getRLEDataWidth() );
  }

  /**
   * Test method for {@link SumpConfig#getRLEDataWidth()}.
   */
  @Test
  public void testGetRLEDataWidthWithoutDDR()
  {
    // Without DDR...
    this.config.setSampleRate( 1 );

    this.config.setEnabledChannels( 0 );
    assertEquals( 0, this.config.getRLEDataWidth() );

    this.config.setEnabledChannels( 0x000000FF );
    assertEquals( 8, this.config.getRLEDataWidth() );

    this.config.setEnabledChannels( 0x0000FFFF );
    assertEquals( 16, this.config.getRLEDataWidth() );

    this.config.setEnabledChannels( 0x00FFFFFF );
    assertEquals( 24, this.config.getRLEDataWidth() );

    this.config.setEnabledChannels( 0xFFFFFFFF );
    assertEquals( 32, this.config.getRLEDataWidth() );
  }

  /**
   * Test method for {@link SumpConfig#getSampleCount()}.
   */
  @Test
  public void testGetSampleCountWithDDR()
  {
    // With DDR...
    this.config.setSampleRate( LogicSnifferAcquisitionTask.CLOCK + 1 );

    this.config.setSampleCount( 7 );
    assertEquals( 0, this.config.getSampleCount() );

    this.config.setSampleCount( 8 );
    assertEquals( 8, this.config.getSampleCount() );

    this.config.setSampleCount( 0xffff8 );
    assertEquals( 0xffff8, this.config.getSampleCount() );

    this.config.setSampleCount( 0xffff9 );
    assertEquals( 0xffff8, this.config.getSampleCount() );

    this.config.setSampleCount( Integer.MAX_VALUE );
    assertEquals( 0xffff8, this.config.getSampleCount() );
  }

  /**
   * Test method for {@link SumpConfig#getSampleCount()}.
   */
  @Test
  public void testGetSampleCountWithoutDDR()
  {
    // Without DDR...
    this.config.setSampleRate( 1 );

    this.config.setSampleCount( 0x03 );
    assertEquals( 0, this.config.getSampleCount() );

    this.config.setSampleCount( 0x04 );
    assertEquals( 0x04, this.config.getSampleCount() );

    this.config.setSampleCount( 0xffffc );
    assertEquals( 0xffffc, this.config.getSampleCount() );

    this.config.setSampleCount( 0xffffd );
    assertEquals( 0xffffc, this.config.getSampleCount() );

    this.config.setSampleCount( Integer.MAX_VALUE );
    assertEquals( 0xffffc, this.config.getSampleCount() );
  }

  /**
   * Test method for {@link SumpConfig#getSampleRate()}.
   */
  @Test
  public void testGetSampleRate()
  {
    this.config.setSampleRate( 1 );
    assertEquals( 1, this.config.getSampleRate() );

    this.config.setSampleRate( 0xFFFFFFE );
    assertEquals( 0xFFFFFFE, this.config.getSampleRate() );

    this.config.setSampleRate( 0xFFFFFFF );
    assertEquals( 0xFFFFFFF, this.config.getSampleRate() );

    this.config.setSampleRate( Integer.MAX_VALUE );
    assertEquals( 0xFFFFFFF, this.config.getSampleRate() );
  }

  /**
   * Test method for {@link SumpConfig#getStopCounter()}.
   */
  @Test
  public void testGetStopCounter()
  {
    this.config.setSampleCount( 1000 );

    this.config.setRatio( 1.0 );
    assertEquals( 1000, this.config.getStopCounter() );

    this.config.setRatio( 0.5 );
    assertEquals( 500, this.config.getStopCounter() );

    this.config.setRatio( 0.1 );
    assertEquals( 100, this.config.getStopCounter() );

    this.config.setRatio( 0.0 );
    assertEquals( 0, this.config.getStopCounter() );
  }

  /**
   * Test method for {@link SumpConfig#isDoubleDataRateEnabled()}.
   */
  @Test
  public void testIsDoubleDataRateEnabled()
  {
    this.config.setSampleRate( 1 );
    assertFalse( this.config.isDoubleDataRateEnabled() );

    this.config.setSampleRate( LogicSnifferAcquisitionTask.CLOCK );
    assertFalse( this.config.isDoubleDataRateEnabled() );

    this.config.setSampleRate( LogicSnifferAcquisitionTask.CLOCK + 1 );
    assertTrue( this.config.isDoubleDataRateEnabled() );
  }

  /**
   * Test method for {@link SumpConfig#isFilterAvailable()}.
   */
  @Test
  public void testIsFilterAvailableWithDDR()
  {
    // With DDR...
    this.config.setSampleRate( LogicSnifferAcquisitionTask.CLOCK + 1 );

    assertFalse( this.config.isFilterAvailable() );
  }

  /**
   * Test method for {@link SumpConfig#isFilterAvailable()}.
   */
  @Test
  public void testIsFilterAvailableWithoutDDR()
  {
    // Without DDR...
    this.config.setSampleRate( 1 );

    assertTrue( this.config.isFilterAvailable() );
  }

  /**
   * Test method for {@link SumpConfig#isGroupEnabled(int)}.
   */
  @Test
  public void testIsGroupEnabledWithDDR()
  {
    // With DDR...
    this.config.setSampleRate( LogicSnifferAcquisitionTask.CLOCK + 1 );

    // One channel group...
    this.config.setEnabledChannels( 0x000000FF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertFalse( this.config.isGroupEnabled( 1 ) );
    assertFalse( this.config.isGroupEnabled( 2 ) );
    assertFalse( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0x0000FF00 );
    assertFalse( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertFalse( this.config.isGroupEnabled( 2 ) );
    assertFalse( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0x00FF0000 );
    assertFalse( this.config.isGroupEnabled( 0 ) );
    assertFalse( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertFalse( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0xFF000000 );
    assertFalse( this.config.isGroupEnabled( 0 ) );
    assertFalse( this.config.isGroupEnabled( 1 ) );
    assertFalse( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );

    // Two channel groups...
    this.config.setEnabledChannels( 0x0000FFFF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertFalse( this.config.isGroupEnabled( 2 ) );
    assertFalse( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0x00FFFF00 );
    assertFalse( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertFalse( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0xFFFF0000 );
    assertFalse( this.config.isGroupEnabled( 0 ) );
    assertFalse( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0xFF00FF00 );
    assertFalse( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertFalse( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0x00FF00FF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertFalse( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertFalse( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0xFF0000FF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertFalse( this.config.isGroupEnabled( 1 ) );
    assertFalse( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );

    // Three channel groups...
    this.config.setEnabledChannels( 0x00FFFFFF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertFalse( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0xFFFFFF00 );
    assertFalse( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0xFFFF00FF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertFalse( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0xFF00FFFF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertFalse( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );

    // Four channel groups...
    this.config.setEnabledChannels( 0xFFFFFFFF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );
  }

  /**
   * Test method for {@link SumpConfig#isGroupEnabled(int)}.
   */
  @Test
  public void testIsGroupEnabledWithoutDDR()
  {
    // Without DDR...
    this.config.setSampleRate( 1 );

    // One channel group...
    this.config.setEnabledChannels( 0x000000FF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertFalse( this.config.isGroupEnabled( 1 ) );
    assertFalse( this.config.isGroupEnabled( 2 ) );
    assertFalse( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0x0000FF00 );
    assertFalse( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertFalse( this.config.isGroupEnabled( 2 ) );
    assertFalse( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0x00FF0000 );
    assertFalse( this.config.isGroupEnabled( 0 ) );
    assertFalse( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertFalse( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0xFF000000 );
    assertFalse( this.config.isGroupEnabled( 0 ) );
    assertFalse( this.config.isGroupEnabled( 1 ) );
    assertFalse( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );

    // Two channel groups...
    this.config.setEnabledChannels( 0x0000FFFF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertFalse( this.config.isGroupEnabled( 2 ) );
    assertFalse( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0x00FFFF00 );
    assertFalse( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertFalse( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0xFFFF0000 );
    assertFalse( this.config.isGroupEnabled( 0 ) );
    assertFalse( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0xFF00FF00 );
    assertFalse( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertFalse( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0x00FF00FF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertFalse( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertFalse( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0xFF0000FF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertFalse( this.config.isGroupEnabled( 1 ) );
    assertFalse( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );

    // Three channel groups...
    this.config.setEnabledChannels( 0x00FFFFFF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertFalse( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0xFFFFFF00 );
    assertFalse( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0xFFFF00FF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertFalse( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );

    this.config.setEnabledChannels( 0xFF00FFFF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertFalse( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );

    // Four channel groups...
    this.config.setEnabledChannels( 0xFFFFFFFF );
    assertTrue( this.config.isGroupEnabled( 0 ) );
    assertTrue( this.config.isGroupEnabled( 1 ) );
    assertTrue( this.config.isGroupEnabled( 2 ) );
    assertTrue( this.config.isGroupEnabled( 3 ) );
  }

  /**
   * Test method for {@link SumpConfig#setEnabledChannels(int)}.
   */
  @Test
  public void testSetEnabledChannels()
  {
    this.config.setEnabledChannels( 0x00 );
    assertEquals( 0, this.config.getEnabledChannelsMask() );

    this.config.setEnabledChannels( 0xFFFFFFFF );
    assertEquals( 0xFFFFFFFF, this.config.getEnabledChannelsMask() );

    this.config.setEnabledChannels( -1 );
    assertEquals( 0xFFFFFFFF, this.config.getEnabledChannelsMask() );
  }

  /**
   * Test method for {@link SumpConfig#setRatio(double)}.
   */
  @Test( expected = IllegalArgumentException.class )
  public void testSetNegativeRatioFail()
  {
    this.config.setRatio( -0.1 ); // should fail!
  }

  /**
   * Test method for {@link SumpConfig#setRatio(double)}.
   */
  @Test
  public void testSetRatioOk()
  {
    this.config.setRatio( 0.0 );
    assertEquals( 0.0, this.config.getRatio(), 1.0e-16 );

    this.config.setRatio( 1.0 );
    assertEquals( 1.0, this.config.getRatio(), 1.0e-16 );
  }

  /**
   * Test method for {@link SumpConfig#setRatio(double)}.
   */
  @Test( expected = IllegalArgumentException.class )
  public void testSetTooGreatRatioFail()
  {
    this.config.setRatio( 1.1 ); // should fail!
  }

  /**
   * Test method for {@link SumpConfig#setSampleCount(int)}.
   */
  @Test( expected = IllegalArgumentException.class )
  public void testSetZeroSampleCountFail()
  {
    this.config.setSampleCount( 0 ); // should fail!
  }

  /**
   * Test method for {@link SumpConfig#setSampleRate(int)}.
   */
  @Test( expected = IllegalArgumentException.class )
  public void testSetZeroSampleRateFail()
  {
    this.config.setSampleRate( 0 ); // should fail!
  }

  /**
   * Test method for {@link SumpConfig#setSampleRate(int)}.
   */
  @Test( expected = IllegalArgumentException.class )
  public void testSetZeroSampleRateFails()
  {
    this.config.setSampleRate( 0 ); // should fail!
  }

}
