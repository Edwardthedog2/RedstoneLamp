package redstonelamp.utils;

public class MinecraftPackets {
	//Login Packets
	public static int ID_CONNECTED_PING_OPEN_CONNECTIONS = 0x01;
	public static int ID_UNCONNECTED_PING_OPEN_CONNECTIONS_1 = 0x02;
	public static int ID_OPEN_CONNECTION_REQUEST_1 = 0x05;
	public static int ID_OPEN_CONNECTION_REPLY_1 = 0x06;
	public static int ID_OPEN_CONNECTION_REQUEST_2 = 0x07;
	public static int ID_OPEN_CONNECTION_REPLY_2 = 0x08;
	public static int ID_INCOMPATIBLE_PROTOCOL_VERSION = 0x1A;
	public static int ID_UNCONNECTED_PING_OPEN_CONNECTIONS_2 = 0x1C;
	public static int ID_ADVERTISE_SYSTEM = 0x1D;
	public static int CustomPacket_1 = 0x80;
	public static int CustomPacket_2 = 0x81;
	public static int CustomPacket_3 = 0x82;
	public static int CustomPacket_4 = 0x83;
	public static int CustomPacket_5 = 0x84;
	public static int CustomPacket_6 = 0x85;
	public static int CustomPacket_7 = 0x86;
	public static int CustomPacket_8 = 0x87;
	public static int CustomPacket_9 = 0x88;
	public static int CustomPacket_10 = 0x89;
	public static int CustomPacket_11 = 0x8A;
	public static int CustomPacket_12 = 0x8B;
	public static int CustomPacket_13 = 0x8C;
	public static int CustomPacket_14 = 0x8D;
	public static int CustomPacket_15 = 0x8E;
	public static int CustomPacket_16 = 0x8F;
	
	//NACK/ACK Packets
	public static int NACK = 0xA0;
	public static int ACK = 0xC0;
	
	//Encapsulated Login Packets
	public static int ClientConnect = 0x09;
	public static int ServerHandshake = 0x10;
	public static int ClientHandshake = 0x13;
	public static int ClientCancelConnect = 0x15;
	
	//Ping/Pong Packets
	public static int PingPacket = 0x00;
	public static int PongPacket = 0x03;
	
	//DATA Packets
	public static int LoginPacket = 0x82;
	public static int LoginStatusPacket = 0x83;
	public static int ReadyPacket = 0x84;
	public static int MessagePacket = 0x85;
	public static int SetTimePacket = 0x86;
	public static int StartGamePacket = 0x87;
	public static int AddMobPacket = 0x88;
	public static int AddPlayerPacket = 0x89;
	public static int RemovePlayerPacket = 0x8a;
	public static int AddEntityPacket = 0x8c;
	public static int RemoveEntityPacket = 0x8d;
	public static int AddItemEntityPacket = 0x8e;
	public static int TakeItemEntityPacket = 0x8f;
	public static int MoveEntityPacket = 0x90;
	public static int MoveEntityPacket_PosRot = 0x93;
	public static int MovePlayerPacket = 0x94;
	public static int PlaceBlockPacket = 0x95;
	public static int RemoveBlockPacket = 0x96;
	public static int UpdateBlockPacket = 0x97;
	public static int AddPaintingPacket = 0x98;
	public static int ExplodePacket = 0x99;
	public static int LevelEventPacket = 0x9a;
	public static int TileEventPacket = 0x9b;
	public static int EntityEventPacket = 0x9c;
	public static int RequestChunkPacket = 0x9d;
	public static int ChunkDataPacket = 0x9e;
	public static int PlayerEquipmentPacket = 0x9f;
	public static int PlayerArmorEquipmentPacket = 0xa0;
	public static int InteractPacket = 0xa1;
	public static int UseItemPacket = 0xa2;
	public static int PlayerActionPacket = 0xa3;
	public static int HurtArmorPacket = 0xa5;
	public static int SetEntityDataPacket = 0xa6;
	public static int SetEntityMotionPacket = 0xa7;
	public static int SetRidingPacket = 0xa8;
	public static int SetHealthPacket = 0xa9;
	public static int SetSpawnPositionPacket = 0xaa;
	public static int AnimatePacket = 0xab;
	public static int RespawnPacket = 0xac;
	public static int SendInventoryPacket = 0xad;
	public static int DropItemPacket = 0xae;
	public static int ContainerOpenPacket = 0xaf;
	public static int ContainerClosePacket = 0xb0;
	public static int ContainerSetSlotPacket = 0xb1;
	public static int ContainerSetDataPacket = 0xb2;
	public static int ContainerSetContentPacket = 0xb3;
	public static int ContainerAckPacket = 0xb4;
	public static int ChatPacket = 0xb5;
	public static int SignUpdatePacket = 0xb6;
	public static int AdventureSettingsPacket = 0xb7;
	
	public int[] LoginPacket() {
		int[] array = {
				ID_CONNECTED_PING_OPEN_CONNECTIONS,
				ID_UNCONNECTED_PING_OPEN_CONNECTIONS_1,
				ID_OPEN_CONNECTION_REQUEST_1,
				ID_OPEN_CONNECTION_REPLY_1,
				ID_OPEN_CONNECTION_REQUEST_2,
				ID_OPEN_CONNECTION_REPLY_2,
				ID_INCOMPATIBLE_PROTOCOL_VERSION,
				ID_UNCONNECTED_PING_OPEN_CONNECTIONS_2,
				ID_ADVERTISE_SYSTEM,
				CustomPacket_1,
				CustomPacket_2,
				CustomPacket_3,
				CustomPacket_4,
				CustomPacket_5,
				CustomPacket_6,
				CustomPacket_7,
				CustomPacket_8,
				CustomPacket_9,
				CustomPacket_10,
				CustomPacket_11,
				CustomPacket_12,
				CustomPacket_13,
				CustomPacket_14,
				CustomPacket_15,
				CustomPacket_16
		};
		return array;
	}
	
	public int[] NACK_ACK_Packet() {
		int[] array = {
				NACK,
				ACK
		};
		return array;
	}
	
	public int[] EncapsulatedLoginPacket() {
		int[] array = {
				ClientConnect,
				ServerHandshake,
				ClientHandshake,
				ClientCancelConnect
		};
		return array;
	}
	
	public int[] PingPongPacket() {
		int[] array = {
				PingPacket,
				PongPacket
		};
		return array;
	}
	
	public int[] DATA_Packet() {
		int[] array = {
				LoginPacket,
				LoginStatusPacket,
				ReadyPacket,
				MessagePacket,
				SetTimePacket,
				StartGamePacket,
				AddMobPacket,
				AddPlayerPacket,
				RemovePlayerPacket,
				AddEntityPacket,
				RemoveEntityPacket,
				AddItemEntityPacket,
				TakeItemEntityPacket,
				MoveEntityPacket,
				MoveEntityPacket_PosRot,
				MovePlayerPacket,
				PlaceBlockPacket,
				RemoveBlockPacket,
				UpdateBlockPacket,
				AddPaintingPacket,
				ExplodePacket,
				LevelEventPacket,
				TileEventPacket,
				EntityEventPacket,
				RequestChunkPacket,
				ChunkDataPacket,
				PlayerEquipmentPacket,
				PlayerArmorEquipmentPacket,
				InteractPacket,
				UseItemPacket,
				PlayerActionPacket,
				HurtArmorPacket,
				SetEntityDataPacket,
				SetEntityMotionPacket,
				SetRidingPacket,
				SetHealthPacket,
				SetSpawnPositionPacket,
				AnimatePacket,
				RespawnPacket,
				SendInventoryPacket,
				DropItemPacket,
				ContainerOpenPacket,
				ContainerClosePacket,
				ContainerSetSlotPacket,
				ContainerSetDataPacket,
				ContainerSetContentPacket,
				ContainerAckPacket,
				ChatPacket,
				SignUpdatePacket,
				AdventureSettingsPacket
		};
		return array;
	}
}
