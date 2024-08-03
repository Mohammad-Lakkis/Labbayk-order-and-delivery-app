using AutoMapper;
using Labbayk.Dto;
using Labbayk.Models;

namespace Labbayk.Helper
{
    public class MappingProfiles : Profile
    {
        public MappingProfiles()
        {
            CreateMap<Item,ItemDto>();
            CreateMap<ItemDto,Item>();
            CreateMap<Order,OrderDto>();
            CreateMap<OrderDto,Order>();
        }
    }
}
