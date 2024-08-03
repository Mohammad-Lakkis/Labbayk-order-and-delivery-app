using Labbayk.Models;

namespace Labbayk.Interfaces
{
    public interface IDriversRepository
    {
        ICollection<Driver> GetDrivers();
        Driver GetDriverByName(string name);
    }
}
